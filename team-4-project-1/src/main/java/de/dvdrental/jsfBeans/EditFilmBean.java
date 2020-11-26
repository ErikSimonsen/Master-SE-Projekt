package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Category;
import de.dvdrental.entities.Film;
import de.dvdrental.repositories.FilmRepository;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EditFilmBean implements Serializable {
    @Inject
    FilmRepository filmRepository;

    private Film film;
    private Category category;

    private Integer filmUrlId;

    public EditFilmBean() {
    }

    @PostConstruct
    public void init() {
    }

    public String update() {
        filmRepository.update(film);
        return "films?faces-redirect=true";
    }

    public void loadFilm() {
        film = filmRepository.get(getFilmUrlId());
    }

    // Create data binding for select tags - Create and Fill an Array of SelectItem Objects with either the Film.Rating enum or Category Objects
    public Object[] getRatings() {
        SelectItem[] items = new SelectItem[Film.Rating.values().length];
        for (int i = 0; i < items.length; i++) {
            Film.Rating rating = Film.Rating.values()[i];
            items[i] = new SelectItem(rating, rating.toString());
        }
        return items;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Integer getFilmUrlId() {
        return filmUrlId;
    }

    public void setFilmUrlId(Integer filmUrlId) {
        this.filmUrlId = filmUrlId;
    }
}
