package de.dvdrental.beans;

import de.dvdrental.beans.services.CustomerRentalsService;
import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Film;
import de.dvdrental.entities.Rental;
import de.dvdrental.microservices.CustomerClient;
import de.dvdrental.microservices.FilmClient;
import de.dvdrental.microservices.StoreClient;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class CustomerRentalsBean implements Serializable {
    @Inject
    StoreClient storeClient;
    @Inject
    CustomerClient customerClient;
    @Inject
    FilmClient filmClient;
    @Inject
    CustomerRentalsService customerRentalsService;
    @Inject
    SessionBean sessionBean;

    private Integer customerId;
    private Customer customer;
    private List<Rental> rentals;
    private Map<Integer, Film> films;

    public CustomerRentalsBean() {
    }

    public void loadUrlParameters() {
        customer = customerClient.getCustomer(customerId);
        rentals = storeClient.getRentalsOfCustomer(customerId);
        films = rentals.stream().collect(Collectors.toMap(Rental::getRentalId, rental -> filmClient.getFilm(rental)));
    }

    public String getActionButtonText(Rental rental) {
        if (rental.getReturnDate() == null)
            return "Film zurückgeben";
        if (!customerRentalsService.isPaid(rental, films.get(rental.getRentalId())))
            return "Betrag begleichen";
        return "";
    }

    public String returnFilmAndPayCosts(Rental rental) {
        if (rental.getReturnDate() == null) {
            storeClient.returnRental(rental);
        }
        Film film = films.get(rental.getRentalId());
        if (!customerRentalsService.isPaid(rental, film)) {
            BigDecimal amount = customerRentalsService.getCostsLeftToPay(rental, film);
            customerClient.createPayment(amount, customerId, rental.getRentalId(), sessionBean.getActiveStaff().getId());
        }
        rental.setReturnDate(storeClient.getRental(rental.getRentalId()).getReturnDate());
        return "";
    }

    //Getter and setter
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public Film getFilm(Rental r) {
        return films.get(r.getRentalId());
    }
}
