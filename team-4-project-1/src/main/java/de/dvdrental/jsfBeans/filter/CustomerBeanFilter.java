package de.dvdrental.jsfBeans.filter;

import de.dvdrental.entities.Country;
import de.dvdrental.jsfBeans.interfaces.BeanFilter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CustomerBeanFilter extends BeanFilter {
    @Size(max = 60, message = "Maximal 60 Zeichen erlaubt")
    private String idName;
    @Max(value = 99999, message = "Nur Zahlen < 99.999 erlaubt")
    @Positive(message = "Geben Sie eine positive Zahl an")
    private Integer plz;
    private Country country;

    public Integer getPlz() {
        return plz;
    }

    public void setPlz(Integer plz) {
        this.plz = plz;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }
}
