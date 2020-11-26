package de.dvdrental.repositories.views;

import de.dvdrental.entities.views.CustomerList;
import de.dvdrental.jsfBeans.filter.CustomerBeanFilter;
import de.dvdrental.jsfBeans.filter.SortingField;
import de.dvdrental.repositories.interfaces.CrudWithFilterRepository;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerListRepository extends CrudWithFilterRepository<CustomerList, CustomerBeanFilter> implements Serializable {
    public CustomerListRepository() {
        super(CustomerList.class);
    }

    @Override
    public List<Predicate> whereClause(CustomerBeanFilter beanFilter, CriteriaBuilder cb, Root<CustomerList> rootEntity) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (beanFilter.getIdName() != null) {
            Predicate name = cb.like(cb.upper(rootEntity.get("name")), beanFilter.getIdName().toUpperCase() + "%");
            Predicate id = cb.like(rootEntity.get("id").as(String.class), beanFilter.getIdName() + "%");
            predicates.add(cb.or(name, id));
        }
        if (beanFilter.getPlz() != null) {
            predicates.add(
                    cb.like(rootEntity.get("zipCode").as(String.class), beanFilter.getPlz() + "%"));
        }
        if (beanFilter.getCountry() != null) {
            predicates.add(
                    cb.equal(rootEntity.get("country"), beanFilter.getCountry().getCountry()));
        }
        return predicates;
    }

    @Override
    public List<Order> orderByClause(CustomerBeanFilter beanFilter, CriteriaBuilder cb, Root<CustomerList> rootEntity) {
        List<Order> orders = new ArrayList<Order>();
        //determine the field, by which the query sorts by
        if (beanFilter.getSortingField() != null) {
            SortingField sortingField = beanFilter.getSortingField();
            Expression<Object> expression;
            switch (sortingField.getFieldName()) {
                case "id":
                    expression = rootEntity.get("id");
                    break;
                case "name":
                    expression = rootEntity.get("name");
                    break;
                case "address":
                    expression = rootEntity.get("address");
                    break;
                case "zipCode":
                    expression = rootEntity.get("zipCode");
                    break;
                case "phone":
                    expression = rootEntity.get("phone");
                    break;
                case "city":
                    expression = rootEntity.get("city");
                    break;
                case "country":
                    expression = rootEntity.get("country");
                    break;
                case "notes":
                    expression = rootEntity.get("notes");
                    break;
                case "sid":
                    expression = rootEntity.get("sid");
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
