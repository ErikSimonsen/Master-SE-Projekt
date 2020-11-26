package de.dvdrental.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Store {
    private int storeId;
    private Timestamp lastUpdate;
    private Staff staff;
    private Address address;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
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
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return storeId == store.storeId &&
                Objects.equals(lastUpdate, store.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, lastUpdate);
    }

    @ManyToOne
    @JoinColumn(name = "manager_staff_id", referencedColumnName = "staff_id", nullable = false)
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeId=" + storeId +
                ", lastUpdate=" + lastUpdate +
                //", staffByManagerStaffId=" + staffByManagerStaffId + // Had to uncomment because of circular dependency (staff to string prints also the store)
                ", address=" + address +
                '}';
    }
}
