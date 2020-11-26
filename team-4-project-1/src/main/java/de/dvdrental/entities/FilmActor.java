package de.dvdrental.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "film_actor", schema = "public", catalog = "dvdrental")
@IdClass(FilmActorPK.class)
public class FilmActor {
    private Timestamp lastUpdate;
    private Actor actor;
    private Film film;

    @Id
    @ManyToOne
    @JoinColumn(name = "actor_id", referencedColumnName = "actor_id", nullable = false)
    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "film_id", nullable = false)
    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Basic
    @Column(name = "last_update", nullable = false, updatable = false, insertable = false)
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
        FilmActor filmActor = (FilmActor) o;
        return Objects.equals(lastUpdate, filmActor.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastUpdate);
    }
}
