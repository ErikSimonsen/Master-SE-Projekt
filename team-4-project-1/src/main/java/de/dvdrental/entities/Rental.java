package de.dvdrental.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Rental {
    private int rentalId;
    private Timestamp rentalDate;
    private Timestamp returnDate;
    private Timestamp lastUpdate;
    private Inventory inventory;
    private Customer customer;
    private Staff staff;

    public Rental() {
    }

    public Rental(Inventory inventory, Customer customer, Staff staff) {
        this.rentalDate = new Timestamp(System.currentTimeMillis()); //TODO: This is not CET time, bad?
        this.returnDate = null;
        this.inventory = inventory;
        this.customer = customer;
        this.staff = staff;
    }

    public Rental(Timestamp rentalDate, Timestamp returnDate, Inventory inventory,
                  Customer customer, Staff staff) {
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.inventory = inventory;
        this.customer = customer;
        this.staff = staff;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    @Basic
    @Column(name = "rental_date", nullable = false)
    public Timestamp getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
    }

    @Basic
    @Column(name = "return_date")
    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    @Basic
    @Column(name = "last_update", nullable = false, updatable = false, insertable = false)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
        return rentalId == rental.rentalId &&
                Objects.equals(rentalDate, rental.rentalDate) &&
                Objects.equals(returnDate, rental.returnDate) &&
                Objects.equals(lastUpdate, rental.lastUpdate) &&
                Objects.equals(inventory, rental.inventory) &&
                Objects.equals(customer, rental.customer) &&
                Objects.equals(staff, rental.staff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, rentalDate, returnDate, lastUpdate, inventory, customer, staff);
    }
}
