package de.dvdrental.resources.proxies;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class RentalPostProxy {
    @NotNull
    private Integer inventory;
    @NotNull
    private Integer customer;
    @NotNull
    private Integer staff;
    @NotNull
    @JsonbDateFormat(value = "yyyy-MM-dd H:mm")
    private LocalDateTime date;

    public RentalPostProxy() {
    }

    public RentalPostProxy(Integer inventory, Integer customer, Integer staff, LocalDateTime date) {
        this.inventory = inventory;
        this.customer = customer;
        this.staff = staff;
        this.date = date;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
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
}
