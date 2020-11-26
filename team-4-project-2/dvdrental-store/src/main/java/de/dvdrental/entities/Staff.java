package de.dvdrental.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Staff {
    private int staffId;
    private String firstName;
    private String lastName;
    private String email;
    private Store store;
    private boolean active;
    private String username;
    private String password;
    private byte[] picture;
    private Address address;

    public Staff() {
        address = new Address();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false)
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    @Column(name = "first_name")
    @Size(min = 3, message = "Der Vorname muss mindestens 3 Buchstaben lang sein!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Size(min = 3, message = "Der Username muss mindestens 3 Zeichen lang sein!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Size(min = 8, message = "Das Passwort muss mindestens 8 Zeichen lang sein!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
