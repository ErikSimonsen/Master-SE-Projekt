package de.dvdrental.beans;

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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class PayRentalsBean implements Serializable {
    @Inject
    private SessionBean sessionBean;
    @Inject
    private StoreClient storeClient;
    @Inject
    private CustomerClient customerClient;
    @Inject
    private FilmClient filmClient;

    private Integer customerId;
    private String rentalIds;
    private Customer customer;
    private List<Rental> rentals;
    private Map<Rental, Film> rentalsToFilm;

    public void loadUrlParameters() {
        customer = customerClient.getCustomer(customerId);

        rentals = Arrays.stream(rentalIds.split(","))
                .map(id -> storeClient.getRental(Integer.valueOf(id)))
                .collect(Collectors.toList());

        rentalsToFilm = rentals.stream().collect(Collectors.toMap(rental -> rental, rental -> filmClient.getFilm(rental)));
    }

    public BigDecimal getCosts() {
        return rentals.stream()
                .map(rental -> rentalsToFilm.get(rental).getRentalRate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String pay() {
        rentals.forEach(rental -> {
            BigDecimal amount = rentalsToFilm.get(rental).getRentalRate();
            customerClient.createPayment(amount, customer.getId(), rental.getRentalId(), sessionBean.getActiveStaff().getId());
        });
        return "customers?faces-redirect=true";
    }

    public Film getFilm(Rental rental) {
        return rentalsToFilm.get(rental);
    }

    //Getter and Setter
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getRentalIds() {
        return rentalIds;
    }

    public void setRentalIds(String rentalIds) {
        this.rentalIds = rentalIds;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}