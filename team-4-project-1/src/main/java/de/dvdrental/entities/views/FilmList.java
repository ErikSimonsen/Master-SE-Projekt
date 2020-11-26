package de.dvdrental.entities.views;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "film_list", schema = "public", catalog = "dvdrental")
public class FilmList {
    private Integer fid;
    private String title;
    private String description;
    private String category;
    private BigDecimal price;
    private Short length;
    private String rating;
    private String actors;

    @Id
    @Basic
    @Column(name = "fid", nullable = false)
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "category", nullable = true, length = 25)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "length", nullable = true)
    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    @Basic
    @Column(name = "rating", nullable = true, length = 5)
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "actors", nullable = true, length = -1)
    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmList filmList = (FilmList) o;
        return Objects.equals(fid, filmList.fid) &&
                Objects.equals(title, filmList.title) &&
                Objects.equals(description, filmList.description) &&
                Objects.equals(category, filmList.category) &&
                Objects.equals(price, filmList.price) &&
                Objects.equals(length, filmList.length) &&
                Objects.equals(rating, filmList.rating) &&
                Objects.equals(actors, filmList.actors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fid, title, description, category, price, length, rating, actors);
    }
}
