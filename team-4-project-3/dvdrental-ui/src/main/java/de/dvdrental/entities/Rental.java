package de.dvdrental.entities;

import de.dvdrental.entities.utils.Href;

import java.util.Date;
import java.util.Objects;

public class Rental {

    private Href customer;
    private Href film;
    private Date rentalDate;
    private Date returnDate;
    private Integer rentalId;
    private Href store;

    public Rental() {
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

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public Href getStore() {
        return store;
    }

    public void setStore(Href store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(rentalId, rental.rentalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "customer=" + customer +
                ", film=" + film +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", rentalId=" + rentalId +
                ", store=" + store +
                '}';
    }
}
