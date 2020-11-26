package de.dvdrental.resources.proxies;

public class StoreProxy {
    private Integer id;

    public StoreProxy() {
    }

    public StoreProxy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
