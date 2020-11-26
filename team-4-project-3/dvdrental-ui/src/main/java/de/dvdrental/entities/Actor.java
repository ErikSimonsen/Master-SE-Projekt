package de.dvdrental.entities;

import de.dvdrental.entities.utils.Href;

import java.util.Objects;

public class Actor {
    private Href films;
    private String firstName;
    private Integer id;
    private String lastName;

    public Actor() {
    }

    public Href getFilms() {
        return films;
    }

    public void setFilms(Href films) {
        this.films = films;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
