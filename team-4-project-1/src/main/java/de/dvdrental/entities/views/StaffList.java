package de.dvdrental.entities.views;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "staff_list", schema = "public", catalog = "dvdrental")
public class StaffList {
    private Integer id;
    private String name;
    private String address;
    private String zipCode;
    private String phone;
    private String city;
    private String country;
    private Integer sid;

    @Id
    @Basic
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "zip code", nullable = true, length = 10)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "city", nullable = true, length = 50)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "country", nullable = true, length = 50)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "sid", nullable = true)
    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffList staffList = (StaffList) o;
        return Objects.equals(id, staffList.id) &&
                Objects.equals(name, staffList.name) &&
                Objects.equals(address, staffList.address) &&
                Objects.equals(zipCode, staffList.zipCode) &&
                Objects.equals(phone, staffList.phone) &&
                Objects.equals(city, staffList.city) &&
                Objects.equals(country, staffList.country) &&
                Objects.equals(sid, staffList.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, zipCode, phone, city, country, sid);
    }
}
