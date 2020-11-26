package de.dvdrental.repositories;

import de.dvdrental.entities.Film;
import de.dvdrental.entities.FilmActor;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class FilmActorRepository extends CrudRepository<FilmActor> {

    public FilmActorRepository() {
        super(FilmActor.class);
    }

    public FilmActor get(FilmActorRepository pk) {
        return em.find(FilmActor.class, pk);
    }

    public List<FilmActor> getForFilm(Film film) {
        return em.createQuery("Select fa from FilmActor fa where fa.film=:film", FilmActor.class)
                .setParameter("film", film).getResultList();
    }
}
