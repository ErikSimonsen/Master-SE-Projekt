package de.dvdrental.resources.proxies;

import de.dvdrental.entities.Category;
import de.dvdrental.entities.Film;
import de.dvdrental.resources.utils.Href;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilmProxy {
    @JsonbProperty("id")
    private int filmId;
    @NotNull
    private String title;
    private String description;
    private Short releaseYear;
    @NotNull
    private BigDecimal rentalRate;
    @NotNull
    private BigDecimal replacementCost;
    @NotNull
    private Short rentalDuration;
    private Short length;
    private String rating;
    private String language;
    private List<String> categories;
    @JsonbProperty("actors")
    private Href actorsHref;

    public FilmProxy() {
        categories = new ArrayList<>();
    }

    public FilmProxy(Film film, Href actorsHref) {
        this.filmId = film.getFilmId();
        this.title = film.getTitle();
        this.description = film.getDescription();
        this.releaseYear = (short) film.getReleaseYear().getValue();
        this.rentalRate = film.getRentalRate();
        this.rating = film.getRating().getShortName();
        this.language = film.getLanguage().getName();
        this.length = film.getLength();
        this.rentalDuration = film.getRentalDuration();
        this.rentalRate = film.getRentalRate();
        this.replacementCost = film.getReplacementCost();
        this.categories = film.getCategories()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        this.actorsHref = actorsHref;

    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public Href getActorsHref() {
        return actorsHref;
    }

    public void setActorsHref(Href actorsHref) {
        this.actorsHref = actorsHref;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public Short getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Short rentalDuration) {
        this.rentalDuration = rentalDuration;
    }
}
