package de.dvdrental.repositories;

import de.dvdrental.entities.Film;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class FilmRepository extends CrudRepository<Film> {
    public FilmRepository() {
        super(Film.class);
    }

    public List<Film> getAll(Integer limit, Integer offset) {
        return em.createQuery("Select f from Film f order by f.filmId", Film.class)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
