package de.dvdrental.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Address {
    private int addressId;
    private String address;
    private String address2;
    private String district;
    private String postalCode;
    private String phone;
    private City city;

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
    @Column(name = "address_id")
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Size(min = 3, message = "Adresse muss mindestens 3 Zeichen lang sein!")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Size(min = 3, message = "District muss mindestens 3 Zeichen lang sein!")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "postal_code")
    @Size(min = 3, message = "Postleitzahl muss mindestens 3 Zeichen lang sein!")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Size(min = 3, message = "Telefonnummer muss mindestens 3 Zeichen lang sein!")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", district='" + district + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", phone='" + phone + '\'' +
                ", city=" + city +
                '}';
    }
}
