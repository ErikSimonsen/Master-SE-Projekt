package de.dvdrental.resources.proxies;

public class InventoryProxy {
    private Integer id;

    public InventoryProxy() {
    }

    public InventoryProxy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
