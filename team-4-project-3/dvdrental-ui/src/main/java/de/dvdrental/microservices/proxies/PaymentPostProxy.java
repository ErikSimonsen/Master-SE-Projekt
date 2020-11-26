package de.dvdrental.microservices.proxies;

import javax.json.bind.annotation.JsonbDateFormat;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentPostProxy {
    private BigDecimal amount;
    private Integer customer;
    @JsonbDateFormat(value = "yyyy-MM-dd H:mm")
    private Date date;
    private Integer rental;
    private Integer staff;

    public PaymentPostProxy() {
    }

    public PaymentPostProxy(BigDecimal amount, Integer customer, Date date, Integer rental, Integer staff) {
        this.amount = amount;
        this.customer = customer;
        this.date = date;
        this.rental = rental;
        this.staff = staff;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRental() {
        return rental;
    }

    public void setRental(Integer rental) {
        this.rental = rental;
    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "PaymentPostProxy{" +
                "amount=" + amount +
                ", customer=" + customer +
                ", date=" + date +
                ", rental=" + rental +
                ", staff=" + staff +
                '}';
    }
}
