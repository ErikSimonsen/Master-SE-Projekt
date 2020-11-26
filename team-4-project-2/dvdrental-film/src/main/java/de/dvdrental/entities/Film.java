package de.dvdrental.entities;

import de.dvdrental.entities.converters.YearAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Year;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Film {
    private int filmId;
    private String title;
    private String description;
    private Year releaseYear;
    private Short rentalDuration;
    private BigDecimal rentalRate;
    private Short length;
    private BigDecimal replacementCost;
    private Rating rating;
    private Language language;
    private Set<Category> categories;
    private List<Actor> actors;

    /**
     * We have to use an ENUM with a String Parameter here, because the values in the database
     * contain hyphens (PG-13, NC-17) which are not legal identifiers. Because of this we also need
     * a converter Class (RatingConverter) which converts a String to the corresponding ENUM value and back, and annotate
     * the getRating()-Method with @Converter(RatingConverter.class).
     * If there wouldn't be hyphens we could just use an ENUM without Parameters and annotate the Rating in the Film-Entity
     * with @Enumerated(EnumType.STRING), that would automatically convert the ENUM value to a String.
     */
    public enum Rating {
        G("G"),
        PG("PG"),
        PG_13("PG-13"),
        R("R"),
        NC_17("NC-17");

        private final String shortName;

        Rating(String shortName) {
            this.shortName = shortName;
        }

        public String getShortName() {
            return shortName;
        }

        /**
         * Iterates trough the Enum Values and checks if the short name of an enum value is equal
         * to the parameter.
         *
         * @param strRating Shortname of an Enum Rating value
         * @return Rating value
         */
        public static Rating fromString(String strRating) {
            for (Rating item : Rating.values()) {
                if (item.getShortName().equals(strRating)) {
                    return item;
                }
            }
            throw new IllegalArgumentException("Rating: " + strRating + " not supported!");
        }
    }

    @Converter(autoApply = true)
    public static class RatingConverter implements AttributeConverter<Rating, String> {
        @Override
        public Rating convertToEntityAttribute(String s) {
            return Rating.fromString(s);
        }

        @Override
        public String convertToDatabaseColumn(Rating rating) {
            return rating.getShortName();
        }
    }

    public Film(String title, String description, Year releaseYear, short rentalDuration,
                BigDecimal rentalRate, Short length, BigDecimal replacementCost, Rating rating, Language language) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.language = language;
    }

    public Film() {
    }

    @Id
    @Column(name = "film_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    @Size(min = 3, message = "Der Titel muss mindestens 3 Buchstaben lang sein!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Size(min = 3, message = "Die Beschreibung muss mindestens 3 Buchstaben lang sein!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "release_year")
    @Convert(converter = YearAttributeConverter.class)
    @Past
    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Column(name = "rental_duration")
    @Min(value = 3, message = "Die Ausleihdauer muss mindestens drei Tage betragen.")
    public Short getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Short rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    @Column(name = "rental_rate")
    @DecimalMin(value = "0.99", message = "Der Film muss mindestens 0.99$ kosten.")
    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    @Min(value = 45, message = "Der Film muss mindestens 45 Minuten lang sein.")
    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    @Column(name = "replacement_cost")
    @DecimalMin(value = "9.99", message = "Die Erstattungskosten muss mindestens 9.99$ betragen.")
    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    @Convert(converter = RatingConverter.class)
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language l) {
        this.language = l;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> category) {
        this.categories = category;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", rentalDuration=" + rentalDuration +
                ", rentalRate=" + rentalRate +
                ", length=" + length +
                ", replacementCost=" + replacementCost +
                ", rating=" + rating +
                ", language=" + language +
                '}';
    }
}
