package de.dvdrental.jsfBeans.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "inventorySizeConverter", managed = true)
public class InventorySizeConverter implements Converter<Integer> {

    @Override
    public Integer getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return Integer.parseInt(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Integer integer) {
        return String.valueOf(integer);
    }
}
