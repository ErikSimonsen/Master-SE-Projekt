package de.dvdrental.jsfBeans.converter;


import de.dvdrental.entities.City;
import de.dvdrental.repositories.CityRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "cityConverter", managed = true)
public class CityConverter implements Converter<City> {

    @Inject
    private CityRepository cityRepository;

    @Override
    public City getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isEmpty())
            return null;
        Integer cityId = Integer.valueOf(value);
        return cityRepository.get(cityId);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, City city) {
        if (city == null)
            return "";
        return String.valueOf(city.getCityId());
    }
}
