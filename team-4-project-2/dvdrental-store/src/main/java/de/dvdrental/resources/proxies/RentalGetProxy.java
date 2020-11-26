package de.dvdrental.resources.proxies;

import de.dvdrental.resources.utils.Href;

import java.time.LocalDateTime;

public class RentalGetProxy {
    private Href customer;
    private Href film;
    private LocalDateTime rentalDate;
    private Integer rentalId;
    private LocalDateTime returnDate;
    private Href store;

    public RentalGetProxy() {
    }

    public RentalGetProxy(Href customer, Href film, LocalDateTime rentalDate, Integer rentalId, LocalDateTime returnDate, Href store) {
        this.customer = customer;
        this.film = film;
        this.rentalDate = rentalDate;
        this.rentalId = rentalId;
        this.returnDate = returnDate;
        this.store = store;
    }

    public Href getCustomer() {
        return customer;
    }

    public void setCustomer(Href customer) {
        this.customer = customer;
    }

    public Href getFilm() {
        return film;
    }

    public void setFilm(Href film) {
        this.film = film;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Href getStore() {
        return store;
    }

    public void setStore(Href store) {
        this.store = store;
    }
}
