package de.dvdrental.resources.proxies;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentPostProxy {
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Integer rental;
    @NotNull
    private Integer customer;
    @NotNull
    private Integer staff;
    @NotNull
    @JsonbDateFormat(value = "yyyy-MM-dd H:mm")
    private LocalDateTime date;

    public PaymentPostProxy() {
    }

    public PaymentPostProxy(BigDecimal amount, Integer rental, Integer customer, Integer staff, LocalDateTime date) {
        this.amount = amount;
        this.rental = rental;
        this.customer = customer;
        this.staff = staff;
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getRental() {
        return rental;
    }

    public void setRental(Integer rental) {
        this.rental = rental;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PaymentPostProxy{" +
                "amount=" + amount +
                ", rental=" + rental +
                ", customer=" + customer +
                ", staff=" + staff +
                ", date=" + date +
                '}';
    }
}
