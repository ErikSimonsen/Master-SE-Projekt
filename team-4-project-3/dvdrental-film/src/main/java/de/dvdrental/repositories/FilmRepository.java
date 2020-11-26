package de.dvdrental.repositories;

import de.dvdrental.entities.Film;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    public List<Film> searchAll(String search, Integer limit, Integer offset) {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Film> q = cb.createQuery(Film.class);
        Root<Film> r = q.from(Film.class);
        Predicate likeFilmId = cb.like(cb.upper(r.get("filmId").as(String.class)), search.toUpperCase() + "%");
        Predicate likeTitle = cb.like(cb.upper(r.get("title")), search.toUpperCase() + "%");
        predicates.add(cb.or(likeTitle, likeFilmId));
        q.select(r)
                .where(predicates.toArray(new Predicate[]{}))
                .orderBy(cb.asc(r.get("filmId")));
        return em.createQuery(q).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
