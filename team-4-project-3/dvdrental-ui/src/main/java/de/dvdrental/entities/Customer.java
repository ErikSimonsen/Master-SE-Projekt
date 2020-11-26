package de.dvdrental.entities;

import de.dvdrental.entities.utils.Href;

import java.util.Objects;

public class Customer {
    private Integer active;
    private Boolean activebool;
    private String email;
    private String firstName;
    private String lastName;
    private Integer id;
    private Href address;

    public Customer() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Href getAddress() {
        return address;
    }

    public void setAddress(Href address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

