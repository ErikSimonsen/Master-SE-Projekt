package de.dvdrental.resources.proxies;

import de.dvdrental.resources.utils.Href;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class CustomerProxy {
    private Integer active;
    @NotNull(message = "Darf nicht leer sein.")
    private Boolean activebool;
    private Href address;
    private LocalDate createDate;
    private String email;
    @NotNull(message = "Darf nicht leer sein.")
    private String firstName;
    private Integer id;
    @NotNull(message = "Darf nicht leer sein.")
    private String lastName;
    private Href store;

    public CustomerProxy() {
    }

    public CustomerProxy(Integer active, Boolean activebool, Href address, LocalDate createDate, String email, String firstName, Integer id, String lastName, Href store) {
        this.active = active;
        this.activebool = activebool;
        this.address = address;
        this.createDate = createDate;
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.store = store;
    }

    public CustomerProxy(Boolean activebool, String firstName, String lastName) {
        this.activebool = activebool;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Boolean getActivebool() {
        return activebool;
    }

    public void setActivebool(Boolean activebool) {
        this.activebool = activebool;
    }

    public Href getAddress() {
        return address;
    }

    public void setAddress(Href address) {
        this.address = address;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Href getStore() {
        return store;
    }

    public void setStore(Href store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "CustomerProxy{" +
                "active=" + active +
                ", activebool=" + activebool +
                ", address=" + address +
                ", createDate=" + createDate +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", id=" + id +
                ", lastName='" + lastName + '\'' +
                ", store=" + store +
                '}';
    }
}
