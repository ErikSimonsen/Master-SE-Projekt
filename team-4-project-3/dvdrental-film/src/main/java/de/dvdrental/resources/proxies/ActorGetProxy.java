package de.dvdrental.resources.proxies;

import de.dvdrental.resources.utils.Href;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;

public class ActorGetProxy {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private int id;
    @JsonbProperty("films")
    private Href filmsHref;

    public ActorGetProxy() {
    }

    public ActorGetProxy(String firstName, String lastName, int id, Href filmsHref) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.filmsHref = filmsHref;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Href getFilmsHref() {
        return filmsHref;
    }

    public void setFilmsHref(Href films) {
        this.filmsHref = films;
    }

}
