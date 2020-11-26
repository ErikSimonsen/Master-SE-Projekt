package de.dvdrental.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Rental {
    private int rentalId;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private Inventory inventory;
    private Staff staff;
    private Integer customerId;

    public Rental() {
    }

    public Rental(LocalDateTime rentalDate, Inventory inventory, Staff staff, Integer customerId) {
        this.rentalDate = rentalDate;
        this.inventory = inventory;
        this.staff = staff;
        this.customerId = customerId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    @Column(name = "rental_date")
    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    @Column(name = "return_date")
    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }


    @ManyToOne
    @JoinColumn(name = "inventory_id")
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @ManyToOne
    @JoinColumn(name = "staff_id")
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Column(name = "customer_id")
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
