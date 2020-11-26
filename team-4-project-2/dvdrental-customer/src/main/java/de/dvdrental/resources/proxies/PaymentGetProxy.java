package de.dvdrental.resources.proxies;

import de.dvdrental.resources.utils.Href;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentGetProxy {
    private BigDecimal amount;
    private Href customer;
    private Href staff;
    private Href rental;
    private Integer id;

    public PaymentGetProxy() {
    }

    public PaymentGetProxy(BigDecimal amount, Href customer, Href staff, Href rental, Integer id) {
        this.amount = amount;
        this.customer = customer;
        this.staff = staff;
        this.rental = rental;
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Href getCustomer() {
        return customer;
    }

    public void setCustomer(Href customer) {
        this.customer = customer;
    }

    public Href getStaff() {
        return staff;
    }

    public void setStaff(Href staff) {
        this.staff = staff;
    }

    public Href getRental() {
        return rental;
    }

    public void setRental(Href rental) {
        this.rental = rental;
    }

    @Override
    public String toString() {
        return "PaymentProxy{" +
                "amount=" + amount +
                ", customer=" + customer +
                ", staff=" + staff +
                ", rental=" + rental +
                ", id=" + id +
                '}';
    }
}
