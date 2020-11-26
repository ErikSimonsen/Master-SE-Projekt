package de.dvdrental.jsfBeans.converter;

import de.dvdrental.entities.Language;
import de.dvdrental.repositories.LanguageRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "languageConverter", managed = true)
public class LanguageConverter implements Converter<Language> {
    @Inject
    LanguageRepository languageRepository;

    @Override
    public Language getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isEmpty())
            return null;
        Integer languageId = Integer.valueOf(value);
        return languageRepository.get(languageId);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Language language) {
        if (language == null)
            return "";
        return String.valueOf(language.getLanguageId());    }
}