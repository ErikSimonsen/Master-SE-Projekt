package de.dvdrental.repositories;

import de.dvdrental.entities.Staff;
import de.dvdrental.jsfBeans.filter.SortingField;
import de.dvdrental.jsfBeans.filter.StaffBeanFilter;
import de.dvdrental.repositories.interfaces.CrudWithFilterRepository;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Named
@Stateless
public class StaffRepository extends CrudWithFilterRepository<Staff, StaffBeanFilter> {
    public StaffRepository() {
        super(Staff.class);
    }

    @Override
    public List<Predicate> whereClause(StaffBeanFilter beanFilter, CriteriaBuilder cb, Root<Staff> rootEntity) {
        return new ArrayList<>();
    }

    @Override
    public List<Order> orderByClause(StaffBeanFilter beanFilter, CriteriaBuilder cb, Root<Staff> rootEntity) {
        List<Order> orders = new ArrayList<>();
        if (beanFilter.getSortingField() != null) {
            SortingField sortingField = beanFilter.getSortingField();
            Path<Object> expression;
            switch (sortingField.getFieldName()) {
                case "id":
                    expression = rootEntity.get("staffId");
                    break;
                case "firstName":
                    expression = rootEntity.get("firstName");
                    break;
                case "lastName":
                    expression = rootEntity.get("lastName");
                    break;
                case "email":
                    expression = rootEntity.get("email");
                    break;
                case "store":
                    expression = rootEntity.get("store");
                    break;
                case "status":
                    expression = rootEntity.get("active");
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
