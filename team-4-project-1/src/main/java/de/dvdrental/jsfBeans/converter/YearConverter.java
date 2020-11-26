package de.dvdrental.jsfBeans.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.time.Year;

@FacesConverter(value = "yearConverter", managed = true)
public class YearConverter implements Converter<Year> {
    @Override
    public Year getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isEmpty())
            return null;

        Year year;
        try {
            year = Year.of(Short.parseShort(value));
        } catch (NumberFormatException e) {
            String msg = "Kein Jahr!";
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }

        if (year.getValue() < 1901 || year.getValue() > 2155) {
            String msg = "Jahr muss zwischen 1901 und 2155 liegen!";
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
        return year;
    }
    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Year year) {
        return String.valueOf(year.getValue());
    }
}
