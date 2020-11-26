package de.dvdrental.beans.services;

import de.dvdrental.entities.Film;
import de.dvdrental.entities.Rental;
import de.dvdrental.microservices.CustomerClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequestScoped
public class CustomerRentalsService {
    //MAX COSTS is 999.99 because the field "amount" in the payments table has a precision 3 and a scale of 2, so max value is 1000.00
    private static final BigDecimal MAX_COSTS = new BigDecimal("999.99");

    @Inject
    CustomerClient customerClient;

    public BigDecimal getCostsPaid(Rental r) {
        BigDecimal sum = customerClient.getSumPaymentsOfRental(r);
        return sum != null ? sum : BigDecimal.ZERO;
    }

    //calculates the "Mahngebühren"
    public BigDecimal getFine(Rental rental, Film film) {
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

    public BigDecimal getLimitedFine(Rental rental, Film film) {
        return MAX_COSTS.subtract(film.getRentalRate()).min(getFine(rental, film));
    }

    public BigDecimal getCosts(Rental rental, Film film) {
        return film.getRentalRate().add(getLimitedFine(rental, film));
    }

    public boolean isPaid(Rental rental, Film film) {
        return getCosts(rental, film).compareTo(getCostsPaid(rental)) == 0;  // Actually getCostsPaid should never be bigger than getCosts
    }

    public BigDecimal getCostsLeftToPay(Rental rental, Film film) {
        return getCosts(rental, film).subtract(getCostsPaid(rental));
    }
}
