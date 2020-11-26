package de.dvdrental.jsfBeans.converter;

import de.dvdrental.entities.Category;
import de.dvdrental.repositories.CategoryRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "categoryConverter", managed = true)
public class CategoryConverter implements Converter<Category> {
    @Inject
    CategoryRepository categoryRepository;

    @Override
    public Category getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return categoryRepository.get(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Category value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getCategoryId());
    }
}
