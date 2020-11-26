package de.dvdrental.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Address {
    private int addressId;
    private String address;
    private String address2;
    private String district;
    private String postalCode;
    private String phone;
    private City city;
    private Timestamp lastUpdate;

    public Address() {
        city = new City();
    }

    public Address(String address, String address2, String district, String postalCode, String phone, City city) {
        this.address = address;
        this.address2 = address2;
        this.district = district;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 50)
    @Size(min = 3, message = "Adresse muss mindestens 3 Zeichen lang sein!")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "address2", length = 50)
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Basic
    @Column(name = "district", nullable = false, length = 20)
    @Size(min = 3, message = "District muss mindestens 3 Zeichen lang sein!")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "postal_code", length = 10)
    @Size(min = 3, message = "Postleitzahl muss mindestens 3 Zeichen lang sein!")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 20)
    @Size(min = 3, message = "Telefonnummer muss mindestens 3 Zeichen lang sein!")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "last_update", nullable = false, updatable = false, insertable = false)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return addressId == address1.addressId &&
                Objects.equals(address, address1.address) &&
                Objects.equals(address2, address1.address2) &&
                Objects.equals(district, address1.district) &&
                Objects.equals(postalCode, address1.postalCode) &&
                Objects.equals(phone, address1.phone) &&
                Objects.equals(city, address1.city) &&
                Objects.equals(lastUpdate, address1.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, address, address2, district, postalCode, phone, city, lastUpdate);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", district='" + district + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", phone='" + phone + '\'' +
                ", city=" + city +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
