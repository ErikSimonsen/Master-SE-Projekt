package de.dvdrental.entities.views;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "sales_by_store", schema = "public", catalog = "dvdrental")
public class SalesByStore {
    private String store;
    private String manager;
    private BigInteger totalSales;

    @Id
    @Basic
    @Column(name = "store", length = -1, nullable = false)
    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Basic
    @Column(name = "manager", length = -1)
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Basic
    @Column(name = "total_sales", nullable = true, precision = 0)
    public BigInteger getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigInteger totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesByStore that = (SalesByStore) o;
        return Objects.equals(store, that.store) &&
                Objects.equals(manager, that.manager) &&
                Objects.equals(totalSales, that.totalSales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(store, manager, totalSales);
    }
}
