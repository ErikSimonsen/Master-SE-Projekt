package de.dvdrental.repositories;

import de.dvdrental.entities.Rental;
import de.dvdrental.jsfBeans.filter.RentalBeanFilter;
import de.dvdrental.jsfBeans.filter.SortingField;
import de.dvdrental.repositories.interfaces.CrudWithFilterRepository;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Named
@Stateless
public class RentalRepository extends CrudWithFilterRepository<Rental, RentalBeanFilter> {
    public RentalRepository() {
        super(Rental.class);
    }

    //need to do joins here to also display film id, name and price
    @Override
    public List<Predicate> whereClause(RentalBeanFilter beanFilter, CriteriaBuilder cb, Root<Rental> rootEntity) {
        List<Predicate> predicates = new ArrayList<>();
        if (beanFilter.getFilmTitle() != null) {
            //always compare it with uppercase strings (because it is case-sensitive)
            Predicate title = cb.like(cb.upper(rootEntity.get("inventory").get("film").get("title")), beanFilter.getFilmTitle().toUpperCase() + "%");

            Predicate filmId = cb.like(rootEntity.get("inventory").get("film").get("filmId").as(String.class), beanFilter.getFilmTitle() + "%");
            predicates.add(cb.or(title, filmId));
        }
        if (beanFilter.getCustomerId() != null) {
            predicates.add(
                    cb.like(rootEntity.get("customer").get("customerId").as(String.class), beanFilter.getCustomerId() + "%"));
        }

        if (beanFilter.getDateFrom() != null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(rootEntity.get("rentalDate"), beanFilter.getDateFrom()));
        }
        if (beanFilter.getDateTo() != null) {
            predicates.add(
                    cb.lessThanOrEqualTo(rootEntity.get("rentalDate"), beanFilter.getDateTo().getTime()));
        }

        if (beanFilter.getReturned() != null) {
            if (beanFilter.getReturned().equals(RentalBeanFilter.Returned.YES)) {
                predicates.add(
                        cb.isNotNull(rootEntity.get("returnDate")));
            } else if (beanFilter.getReturned().equals(RentalBeanFilter.Returned.NO)) {
                predicates.add(
                        cb.isNull(rootEntity.get("returnDate")));
            }
        }
        return predicates;
    }

    @Override
    public List<Order> orderByClause(RentalBeanFilter beanFilter, CriteriaBuilder cb, Root<Rental> rootEntity) {
        List<Order> orders = new ArrayList<>();
        if (beanFilter.getSortingField() != null) {
            SortingField sortingField = beanFilter.getSortingField();
            Path<Object> expression;
            switch (sortingField.getFieldName()) {
                case "id":
                    expression = rootEntity.get("rentalId");
                    break;
                case "customerId":
                    expression = rootEntity.get("customer").get("customerId");
                    break;
                case "filmId":
                    expression = rootEntity.get("inventory").get("film").get("filmId");
                    break;
                case "filmName":
                    expression = rootEntity.get("inventory").get("film").get("title");
                    break;
                case "rentalPrice":
                    expression = rootEntity.get("inventory").get("film").get("rentalRate");
                    break;
                case "rentalDate":
                    expression = rootEntity.get("rentalDate");
                    break;
                case "returnDate":
                    expression = rootEntity.get("returnDate");
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
