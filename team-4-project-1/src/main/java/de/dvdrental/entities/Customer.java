package de.dvdrental.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Customer {
    private int customerId;
    private Store store;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activebool;
    private Timestamp createDate;
    private Timestamp lastUpdate;
    private Integer active;
    private Address address;

    public Customer() {
        address = new Address();
    }

    public Customer(Store store, String firstName, String lastName, String email, boolean activebool, Integer active, Address address) {
        this.store = store;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activebool = activebool;
        this.active = active;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    @Size(min = 3, message = "Der Vorname muss mindestens 3 Buchstaben lang sein!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    @Size(min = 3, message = "Der Nachname muss mindestens 3 Buchstaben lang sein!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "email", length = 50)
    @Email(message = "Email-Adresse ist inkorrekt!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "activebool", nullable = false)
    public boolean isActivebool() {
        return activebool;
    }

    public void setActivebool(boolean activebool) {
        this.activebool = activebool;
    }

    @Basic
    @Column(name = "create_date", nullable = false, updatable = false, insertable = false)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "last_update", nullable = false, insertable = false)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Basic
    @Column(name = "active")
    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId &&
                store.equals(customer.store) &&
                activebool == customer.activebool &&
                firstName.equals(customer.firstName) &&
                lastName.equals(customer.lastName) &&
                email.equals(customer.email) &&
                createDate.equals(customer.createDate) &&
                lastUpdate.equals(customer.lastUpdate) &&
                active.equals(customer.active) &&
                address.equals(customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, store, firstName, lastName, email, activebool, createDate, lastUpdate, active, address);
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", storeId=" + store +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", activebool=" + activebool +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", active=" + active +
                ", address=" + address +
                '}';
    }
}
