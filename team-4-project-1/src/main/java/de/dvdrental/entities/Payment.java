package de.dvdrental.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Payment {
    private int paymentId;
    private BigDecimal amount;
    private Timestamp paymentDate;
    private Customer customer;
    private Staff staff;
    private Rental rental;

    public Payment(BigDecimal amount, Customer customer, Staff staff, Rental rental) {
        this.amount = amount;
        this.customer = customer;
        this.staff = staff;
        this.rental = rental;
        this.paymentDate = new Timestamp(System.currentTimeMillis());
    }

    public Payment() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    @Basic
    @Column(name = "amount", nullable = false, precision = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "payment_date", nullable = false)
    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
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

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return paymentId == payment.paymentId &&
                Objects.equals(amount, payment.amount) &&
                Objects.equals(paymentDate, payment.paymentDate) &&
                Objects.equals(customer, payment.customer) &&
                Objects.equals(staff, payment.staff) &&
                Objects.equals(rental, payment.rental);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, amount, paymentDate, customer, staff, rental);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", customer=" + customer +
                ", staff=" + staff +
                ", rental=" + rental +
                '}';
    }
}
