package de.dvdrental.entities;

import java.io.Serializable;
import java.util.Objects;

public class FilmActorPK implements Serializable {
    private int actor;
    private int film;

    public int getActor() {
        return actor;
    }

    public void setActor(int actor) {
        this.actor = actor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorPK that = (FilmActorPK) o;
        return Objects.equals(actor, that.actor) &&
                Objects.equals(film, that.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actor, film);
    }

    public int getFilm() {
        return film;
    }

    public void setFilm(int filmId) {
        this.film = filmId;
    }
}
