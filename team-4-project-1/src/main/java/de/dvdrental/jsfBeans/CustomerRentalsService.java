package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Film;
import de.dvdrental.entities.Rental;
import de.dvdrental.repositories.PaymentRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;

@Stateless
@Named
public class CustomerRentalsService {
    //MAX COSTS is 999.99 because the field "amount" in the payments table has a precision 3 and a scale of 2, so max value is 1000.00
    private static final BigDecimal MAX_COSTS = new BigDecimal("999.99");

    @Inject
    PaymentRepository paymentRepository;

    public BigDecimal getCostsPaid(Rental r) {
        BigDecimal sum = paymentRepository.getPaymentsSumForRental(r);
        return sum != null ? sum : BigDecimal.ZERO;
    }

    //calculates the "Mahngebühren"
    public BigDecimal getFine(Rental rental) {
        Film film = rental.getInventory().getFilm();
        //First calulating the timespan of the rental
        long time = System.currentTimeMillis();
        if (rental.getReturnDate() != null) {
            time = rental.getReturnDate().getTime();
        }
        long span = time - rental.getRentalDate().getTime();
        int actualRentalDuration = (int) Math.ceil(span / 86400000.0); //in days
        int shouldRentalDuration = film.getRentalDuration();
        int numDaysOver = actualRentalDuration - shouldRentalDuration;

        BigDecimal fine = BigDecimal.ZERO;
        if (numDaysOver > 0) {
            // The user has to get fined
            if (numDaysOver > shouldRentalDuration * 2) {
                //User has to pay replacement cost
                fine = fine.add(film.getReplacementCost());
            }
            //The user also has to pay for each day over one dollar
            fine = fine.add(BigDecimal.valueOf(numDaysOver));
        }
        return fine;
    }

    public BigDecimal getLimitedFine(Rental rental) {
        return MAX_COSTS.subtract(rental.getInventory().getFilm().getRentalRate()).min(getFine(rental));
    }

    public BigDecimal getCosts(Rental rental) {
        return rental.getInventory().getFilm().getRentalRate().add(getLimitedFine(rental));
    }

    public boolean isPaid(Rental rental) {
        return getCosts(rental).compareTo(getCostsPaid(rental)) == 0;  // Actually getCostsPaid should never be bigger than getCosts
    }

    public BigDecimal getCostsLeftToPay(Rental rental) {
        return getCosts(rental).subtract(getCostsPaid(rental));
    }

    public BigDecimal getOverallCostsLeftToPay(List<Rental> rentals) {
        return rentals.stream()
                .map(this::getCostsLeftToPay)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
