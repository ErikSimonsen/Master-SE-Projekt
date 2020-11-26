package de.dvdrental.beans;

import de.dvdrental.entities.Actor;
import de.dvdrental.entities.Film;
import de.dvdrental.microservices.FilmClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EditFilmBean implements Serializable {
    @Inject
    FilmClient filmClient;

    private Integer filmUrlId;
    private Film film;
    private List<Actor> actors;

    public EditFilmBean() {
    }

    public void loadFilm() {
        film = filmClient.getFilm(filmUrlId);
        actors = filmClient.getActorsOfFilm(filmUrlId);
    }

    public String update() {
        actors.forEach(actor -> filmClient.addActorToFilm(filmUrlId, actor.getId()));
        film.getCategories().forEach(category -> filmClient.addCategoryToFilm(filmUrlId, category));
        return "films?faces-redirect=true";
    }

    public Integer getFilmUrlId() {
        return filmUrlId;
    }

    public void setFilmUrlId(Integer filmUrlId) {
        this.filmUrlId = filmUrlId;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
