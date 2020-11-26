package de.dvdrental.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class City {
    private int cityId;
    private String city;
    private Timestamp lastUpdate;
    private Country country;

    public City() {
    }

    public City(String city, Country country) {
        this.city = city;
        this.country = country;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "city", nullable = false, length = 50)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "last_update",  nullable = false, updatable = false, insertable = false)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city1 = (City) o;
        return cityId == city1.cityId &&
                Objects.equals(city, city1.city) &&
                Objects.equals(lastUpdate, city1.lastUpdate) &&
                Objects.equals(country, city1.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, city, lastUpdate, country);
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", city='" + city + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", country=" + country +
                '}';
    }
}
