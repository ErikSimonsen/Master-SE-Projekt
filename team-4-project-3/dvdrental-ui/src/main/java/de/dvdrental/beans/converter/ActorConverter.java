package de.dvdrental.beans.converter;

import de.dvdrental.entities.Actor;
import de.dvdrental.microservices.FilmClient;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "actorConverter", managed = true)
public class ActorConverter implements Converter<Actor> {
    @Inject
    FilmClient filmClient;

    @Override
    public Actor getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return filmClient.getActor(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Actor value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}
