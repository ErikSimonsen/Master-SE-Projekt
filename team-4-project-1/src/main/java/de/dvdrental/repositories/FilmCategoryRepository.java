package de.dvdrental.repositories;

import de.dvdrental.entities.Film;
import de.dvdrental.entities.FilmCategory;
import de.dvdrental.entities.FilmCategoryPK;
import de.dvdrental.jsfBeans.filter.FilmBeanFilter;
import de.dvdrental.jsfBeans.filter.SortingField;
import de.dvdrental.repositories.interfaces.CrudWithFilterRepository;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@Stateless
public class FilmCategoryRepository extends CrudWithFilterRepository<FilmCategory, FilmBeanFilter> implements Serializable {

    public FilmCategoryRepository() {
        super(FilmCategory.class);
    }

    public FilmCategory get(FilmCategoryPK pk) {
        return em.find(FilmCategory.class, pk);
    }

    public FilmCategory getForFilm(Film film) {
        return em.createQuery("Select ca from FilmCategory ca where ca.film =:film", FilmCategory.class)
                .setParameter("film", film).getSingleResult();
    }
    @Override
    public List<Predicate> whereClause(FilmBeanFilter beanFilter, CriteriaBuilder cb, Root<FilmCategory> rootEntity) {
        List<Predicate> predicates = new ArrayList<>();
        //filters / conditions of the query
        if (beanFilter.getFilmTitle() != null) {
            //always compare it with uppercase strings (because it is case-sensitive)
            Predicate title = cb.like(cb.upper(rootEntity.get("film").get("title")), beanFilter.getFilmTitle().toUpperCase() + "%");

            Predicate filmId = cb.like(rootEntity.get("film").get("filmId").as(String.class), beanFilter.getFilmTitle() + "%");
            predicates.add(cb.or(title, filmId));
        }
        if (beanFilter.getFilmCategory() != null) {
            predicates.add(
                    cb.equal(rootEntity.get("category").get("name"), beanFilter.getFilmCategory().getName()));
        }
        if (beanFilter.getLength() != null) {
            predicates.add(
                    cb.equal(rootEntity.get("film").get("length"), beanFilter.getLength()));
        }
        if (beanFilter.getReleaseYear() != null) {
            predicates.add(
                    cb.equal(rootEntity.get("film").get("releaseYear"), beanFilter.getReleaseYear()));
        }
        if (beanFilter.getRating() != null) {
            predicates.add(
                    cb.equal(rootEntity.get("film").get("rating"), beanFilter.getRating()));
        }
        return predicates;
    }

    @Override
    public List<Order> orderByClause(FilmBeanFilter beanFilter, CriteriaBuilder cb, Root<FilmCategory> rootEntity) {
        List<Order> orders = new ArrayList<>();
        //determine the field, by which the query sorts by
        if (beanFilter.getSortingField() != null) {
            SortingField sortingField = beanFilter.getSortingField();
            Path<Object> expression;

            switch (sortingField.getFieldName()) {
                case "id":
                    expression = rootEntity.get("film").get("filmId");
                    break;
                case "title":
                    expression = rootEntity.get("film").get("title");
                    break;
                case "name":
                    expression = rootEntity.get("category").get("name");
                    break;
                case "releaseYear":
                    expression = rootEntity.get("film").get("releaseYear");
                    break;
                case "length":
                    expression = rootEntity.get("film").get("length");
                    break;
                case "rating":
                    expression = rootEntity.get("film").get("rating");
                    break;
                case "rentalDuration":
                    expression = rootEntity.get("film").get("rentalDuration");
                    break;
                case "rentalRate":
                    expression = rootEntity.get("film").get("rentalRate");
                    break;
                case "replacementCost":
                    expression = rootEntity.get("film").get("replacementCost");
                    break;
                default:
                    expression = null;
                    break;
            }
            if (sortingField.getAsc()) {
                orders.add(
                        cb.asc(expression));
            } else {
                orders.add(
                        cb.desc(expression));
            }
        }
        return orders;
    }
}
