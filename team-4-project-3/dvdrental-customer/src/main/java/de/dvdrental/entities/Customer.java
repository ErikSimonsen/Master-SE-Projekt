package de.dvdrental.entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Customer {
    private Integer customerId;
    private Integer storeID;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean activebool;
    private LocalDate createDate;
    private Integer active;
    private Address address;

    public Customer(Integer storeID, String firstName, String lastName, String email, Boolean activebool, LocalDate createDate, Integer active, Address address) {
        //this.customerId = customerId;
        this.storeID = storeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activebool = activebool;
        this.createDate = createDate;
        this.active = active;
        this.address = address;
    }

    public Customer() {
        address = new Address();
    }

    @Column(name = "store_id", nullable = false)
    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer store_id) {
        this.storeID = store_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Column(name = "first_name", nullable = false, length = 45)
    @Size(min = 3, message = "Der Vorname muss mindestens 3 Buchstaben lang sein!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false, length = 45)
    @Size(min = 3, message = "Der Nachname muss mindestens 3 Buchstaben lang sein!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Email(message = "Email-Adresse ist inkorrekt!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActivebool() {
        return activebool;
    }

    public void setActivebool(Boolean activebool) {
        this.activebool = activebool;
    }

    @Column(name = "create_date")
    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
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
                ", storeId=" + storeID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", activebool=" + activebool +
                ", createDate=" + createDate +
                ", active=" + active +
                ", address=" + address +
                '}';
    }
}
