package de.dvdrental.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Inventory {
    private int inventoryId;
    private Timestamp lastUpdate;
    private Film film;
    private Store store;

    public Inventory() {
    }

    public Inventory(Film film, Store store) {
        this.film = film;
        this.store = store;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false)
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Basic
    @Column(name = "last_update", nullable = false, updatable = false, insertable = false)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory inventory = (Inventory) o;
        return inventoryId == inventory.inventoryId &&
                Objects.equals(lastUpdate, inventory.lastUpdate) &&
                Objects.equals(film, inventory.film) &&
                Objects.equals(store, inventory.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, lastUpdate, film, store);
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "film_id", nullable = false)
    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", lastUpdate=" + lastUpdate +
                ", film=" + film +
                ", store=" + store +
                '}';
    }
}
