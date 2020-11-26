package de.dvdrental.resources.proxies;

import java.util.List;

public class InventoryStatusProxy {
    private Long numberOfRents;
    private Long stockNumber;
    private Boolean available;
    private List<InventoryProxy> freeInventories;

    public InventoryStatusProxy() {
    }

    public InventoryStatusProxy(Long numberOfRents, Long stockNumber, Boolean available, List<InventoryProxy> freeInventories) {
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

    public List<InventoryProxy> getFreeInventories() {
        return freeInventories;
    }

    public void setFreeInventories(List<InventoryProxy> freeInventories) {
        this.freeInventories = freeInventories;
    }
}
