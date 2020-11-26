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
    private Timestamp lastUpdate;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Basic
    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 16)
    @Size(min = 3, message = "Der Username muss mindestens 3 Zeichen lang sein!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", length = 40)
    @Size(min = 8, message = "Das Passwort muss mindestens 8 Zeichen lang sein!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "last_update", nullable = false, updatable = false, insertable = false)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Basic
    @Column(name = "picture")
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return staffId == staff.staffId &&
                active == staff.active &&
                Objects.equals(store, staff.store) &&
                Objects.equals(firstName, staff.firstName) &&
                Objects.equals(lastName, staff.lastName) &&
                Objects.equals(email, staff.email) &&
                Objects.equals(username, staff.username) &&
                Objects.equals(password, staff.password) &&
                Objects.equals(lastUpdate, staff.lastUpdate) &&
                Arrays.equals(picture, staff.picture);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", store=" + store +
                ", active=" + active +
                ", username='" + username + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(staffId, firstName, lastName, email, store, active, username, password, lastUpdate);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
