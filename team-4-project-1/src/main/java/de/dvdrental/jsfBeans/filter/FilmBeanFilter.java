package de.dvdrental.jsfBeans.filter;

import de.dvdrental.entities.Category;
import de.dvdrental.entities.Film;
import de.dvdrental.jsfBeans.interfaces.BeanFilter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Year;

public class FilmBeanFilter extends BeanFilter {
    @Size(max = 40, message = "Maximal 40 Zeichen erlaubt")
    private String filmTitle;
    private Category filmCategory;
    @Past
    private Year releaseYear;
    @Max(value = 999, message = "Geben Sie einen Wert < 999 an")
    @Positive(message = "Geben Sie einen positiven Wert an")
    private Short length;
    private Film.Rating rating;

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public Category getFilmCategory() {
        return filmCategory;
    }

    public void setFilmCategory(Category filmCategory) {
        this.filmCategory = filmCategory;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public Film.Rating getRating() {
        return rating;
    }

    public void setRating(Film.Rating rating) {
        this.rating = rating;
    }
}
