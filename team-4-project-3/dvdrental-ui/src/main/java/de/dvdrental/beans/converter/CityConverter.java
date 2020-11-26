package de.dvdrental.beans.converter;


import de.dvdrental.entities.City;
import de.dvdrental.microservices.CustomerClient;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "cityConverter", managed = true)
public class CityConverter implements Converter<City> {

    @Inject
    private CustomerClient customerClient;

    @Override
    public City getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isEmpty())
            return null;
        Integer cityId = Integer.valueOf(value);
        City city = customerClient.getCity(cityId);
        System.out.println("city converter: " + city.getCity());
        return city;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, City city) {
        if (city == null)
            return "";
        return String.valueOf(city.getId());
    }
}
