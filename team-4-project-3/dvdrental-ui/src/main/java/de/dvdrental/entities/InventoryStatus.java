package de.dvdrental.entities;

import java.util.List;

public class InventoryStatus {
    private Long numberOfRents;
    private Long stockNumber;
    private Boolean available;
    private List<Inventory> freeInventories;

    public InventoryStatus() {
    }

    public InventoryStatus(Long numberOfRents, Long stockNumber, Boolean available, List<Inventory> freeInventories) {
        this.numberOfRents = numberOfRents;
        this.stockNumber = stockNumber;
        this.available = available;
        this.freeInventories = freeInventories;
    }

    public Long getNumberOfRents() {
        return numberOfRents;
    }

    public void setNumberOfRents(Long numberOfRents) {
        this.numberOfRents = numberOfRents;
    }

    public Long getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Long stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Inventory> getFreeInventories() {
        return freeInventories;
    }

    public void setFreeInventories(List<Inventory> freeInventories) {
        this.freeInventories = freeInventories;
    }
}
