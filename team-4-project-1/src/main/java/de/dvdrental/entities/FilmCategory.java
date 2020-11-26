package de.dvdrental.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "film_category", schema = "public", catalog = "dvdrental")
@IdClass(FilmCategoryPK.class)
public class FilmCategory {
    @Id
    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "film_id", nullable = false)
    private Film film;
    @Id
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Category category;
    @Basic
    @Column(name = "last_update", nullable = false, updatable = false, insertable = false)
    private Timestamp lastUpdate;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmCategory that = (FilmCategory) o;
        return Objects.equals(lastUpdate, that.lastUpdate) &&
                Objects.equals(film, that.film) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastUpdate, film, category);
    }

    @Override
    public String toString() {
        return "FilmCategory{" +
                "lastUpdate=" + lastUpdate +
                ", film=" + film +
                ", category=" + category +
                '}';
    }
}
