package de.dvdrental.jsfBeans.converter;

import de.dvdrental.entities.Store;
import de.dvdrental.repositories.StoreRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "storeConverter", managed = true)
public class StoreConverter implements Converter<Store> {
    @Inject
    StoreRepository storeRepository;

    @Override
    public Store getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return storeRepository.get(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Store value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getStoreId());
    }
}
