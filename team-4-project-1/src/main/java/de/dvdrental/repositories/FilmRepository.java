package de.dvdrental.repositories;

import de.dvdrental.entities.Film;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class FilmRepository extends CrudRepository<Film> {
    public FilmRepository() {
        super(Film.class);
    }
}
