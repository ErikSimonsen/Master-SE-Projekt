package de.dvdrental.microservices.proxies;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

public class RentalPostProxy {
    private Integer inventory;
    private Integer customer;
    private Integer staff;
    @JsonbDateFormat(value = "yyyy-MM-dd H:mm")
    private Date date;

    public RentalPostProxy() {
    }

    public RentalPostProxy(Integer inventory, Integer customer, Integer staff, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RentalPostProxy{" +
                "inventory=" + inventory +
                ", customer=" + customer +
                ", staff=" + staff +
                ", date=" + date +
                '}';
    }
}
