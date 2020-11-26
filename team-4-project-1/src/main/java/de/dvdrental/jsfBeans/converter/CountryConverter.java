package de.dvdrental.jsfBeans.converter;

import de.dvdrental.entities.Country;
import de.dvdrental.repositories.CountryRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "countryConverter", managed = true)
public class CountryConverter implements Converter<Country> {

    @Inject
    CountryRepository countryRepository;

    @Override
    public Country getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return countryRepository.get(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Country value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getCountryId());
    }
}
