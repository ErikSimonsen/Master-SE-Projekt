package de.dvdrental.resources.proxies;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class AddressProxy {
    @NotNull
    private String address;
    private String address2;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @NotNull
    private String district;
    private Integer id;
    @NotNull
    private String phone;
    private String postalCode;

    public AddressProxy() {
    }

    public AddressProxy(String address, String address2, String city, String country, String district, Integer id, String phone, String postalCode) {
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.country = country;
        this.district = district;
        this.id = id;
        this.phone = phone;
        this.postalCode = postalCode;
    }

    public AddressProxy(String address, String city, String country, String district, String phone) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.district = district;
        this.phone = phone;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AddressProxy{" +
                "address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", district='" + district + '\'' +
                ", id=" + id +
                ", phone='" + phone + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
