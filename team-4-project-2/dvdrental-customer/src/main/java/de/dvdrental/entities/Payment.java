package de.dvdrental.entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Payment {
    private int paymentId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private Customer customer;
    private int staffId;
    private int rentalId;

    public Payment(BigDecimal amount, LocalDateTime paymentDate, Customer customer, int staffId, int rentalId) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.customer = customer;
        this.staffId = staffId;
        this.rentalId = rentalId;
    }

    public Payment() {
    }

    @Column(name = "staff_id")
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    @Column(name = "rental_id")
    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "payment_date")
    @JsonbTransient
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", customer=" + customer +
                ", staffId=" + staffId +
                ", rentalId=" + rentalId +
                '}';
    }
}
