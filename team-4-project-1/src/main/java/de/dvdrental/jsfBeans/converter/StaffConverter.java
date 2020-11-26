package de.dvdrental.jsfBeans.converter;


import de.dvdrental.entities.Staff;
import de.dvdrental.repositories.StaffRepository;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "staffConverter", managed = true)
public class StaffConverter implements Converter<Staff> {
    @Inject
    StaffRepository staffRepository;

    @Override
    public Staff getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        Integer staffId = Integer.valueOf(value);
        try {
            return staffRepository.get(staffId);
        } catch (Exception e) {
            final String msg = "Es konnte kein Mitarbeiter gefunden werden!";
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Staff value) {
        if (value == null) { //this is for example the case if (at login.xhtml) the <f:selectItem is selected, https://stackoverflow.com/questions/17052503/using-a-please-select-fselectitem-with-null-empty-value-inside-a-pselectonem
            //you have to return an empty string. If the jsf code is rendered, the itemValue for the  <f:selectItem> is also rendered.
            //Because that element just exists for the user it has a itemValue of null. Now in the rendering process the getAsString Method is called on the itemValue Attributes
            //of the jsf-Elements, and the value that gets returned is then written into the real html. If the returned value is null the attribute wont even get written. In this
            //specific case that means that the <option> tag does not have a value attribute (you can check that if you change this condition to return null, and then look at
            //the html-code). The Problem now is, that if an option tag has no value attribute, instead its text-value (itemLabel) is submitted, when the form is submitted.
            //In this case that means the "Bitte auswählen" is submitted and processed by the getAsObject-Method and the Integer.valueOf Method does not work on that string.
            //https://stackoverflow.com/questions/7022965/why-selectonemenu-send-itemlabel-to-the-converter
            return "";
        }
        Integer id = value.getStaffId();
        return String.valueOf(id);
    }
}
