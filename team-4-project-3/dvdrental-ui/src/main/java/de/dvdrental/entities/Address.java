package de.dvdrental.entities;

import javax.validation.constraints.Size;

public class Address {
    @Size(min = 3, message = "Adresse muss mindestens 3 Zeichen lang sein!")
    private String address;
    private String city;
    private String country;
    @Size(min = 3, message = "District muss mindestens 3 Zeichen lang sein!")
    private String district;
    private Integer id;
    @Size(min = 3, message = "Telefonnummer muss mindestens 3 Zeichen lang sein!")
    private String phone;
    @Size(min = 3, message = "Postleitzahl muss mindestens 3 Zeichen lang sein!")
    private String postalCode;

    public Address() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
