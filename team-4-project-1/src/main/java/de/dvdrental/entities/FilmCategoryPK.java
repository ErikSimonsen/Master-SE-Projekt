package de.dvdrental.entities;

import java.io.Serializable;
import java.util.Objects;

public class FilmCategoryPK implements Serializable {
    private int film;
    private int category;

    public FilmCategoryPK() {

    }

    public FilmCategoryPK(int filmId, int categoryId) {
        this.film = filmId;
        this.category = categoryId;
    }

    public int getFilm() {
        return film;
    }

    public void setFilm(int film) {
        this.film = film;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmCategoryPK that = (FilmCategoryPK) o;
        return film == that.film &&
                category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, category);
    }
}
