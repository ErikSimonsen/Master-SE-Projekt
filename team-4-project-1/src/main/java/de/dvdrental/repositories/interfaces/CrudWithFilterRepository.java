package de.dvdrental.repositories.interfaces;

import de.dvdrental.jsfBeans.interfaces.BeanFilter;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Extends the normal Crud - Functionality by adding a Count() and GetAll() Method that considers defined Filters and a Sort - Command and allows to specifically filter for specific criteria
 * As the Filters are individual for each Table or DataView the Creation of the Where- and Sort-By Clause have to be implemented manually for each FilterClass (which is usually tightly bound
 * to a JSF Backing Bean). As the criteria are not known in this abstract class already, the commands/filters are generated "dynamically" instead of writing a single sql-query, so
 * you have to use the CriteriaBuilder API here to build the Sql Query step by step.
 *
 * @param <EntityType>
 * @param <FilterType>
 */
public abstract class CrudWithFilterRepository<EntityType, FilterType extends BeanFilter> extends CrudRepository<EntityType> {
    /**
     * @param entityClassType class-type e.g. Actor.class
     */
    public CrudWithFilterRepository(Class<EntityType> entityClassType) {
        super(entityClassType);
    }

    /**
     * Select all Entities (starting from fromRow and selecting rowCount Elements)  from this.table and include the filters and order the data.
     *
     * @param beanFilter Filter, contains the fields which are needed to generate the where-Clause and orderBy-Clause. Is filled in films.xhtml with the values of the inputs and is set by clicking on the sort command links.
     * @param fromRow    Starting row number
     * @param rowCount   Defines how many rows are selected
     * @return List
     */
    public List<EntityType> getAll(FilterType beanFilter, int fromRow, int rowCount) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<EntityType> query = criteriaBuilder.createQuery(entityClass);
        Root<EntityType> rootEntity = query.from(entityClass);

        List<Predicate> predicates = this.whereClause(beanFilter, criteriaBuilder, rootEntity);
        List<Order> orders = this.orderByClause(beanFilter, criteriaBuilder, rootEntity);

        query.select(rootEntity)
                .where(predicates.toArray(new Predicate[]{})).orderBy(orders);
        return em.createQuery(query).setFirstResult(fromRow).setMaxResults(rowCount).getResultList();
    }

    /**
     * Does a Count Command, that selects the data with all specified filters and then returns how many rows are affected. This is for example needed in the Pagination-Functionality to calculate
     * how many pages there are for the user to select.
     * @param beanFilter Filter, contains the fields which are needed to generate the where-Clause and orderBy-Clause. Is filled in films.xhtml with the values of the inputs and is set by clicking on the sort command links.
     * @return Long
     */
    public Long count(FilterType beanFilter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<EntityType> filmCategory = query.from(entityClass);

        List<Predicate> predicates = this.whereClause(beanFilter, criteriaBuilder, filmCategory);

        query.select(criteriaBuilder.count(filmCategory))
                .where(predicates.toArray(new Predicate[]{}));
        return em.createQuery(query).getSingleResult();
    }

    /**
     * @param beanFilter FilterType Specific Filter-Object that extends the BeanFilter-Class and contains the properties which are used as filter criteria and a SortingField-Object
     * @param cb         CriteriaBuilder-Object which creates the filter parameters (so called Predicate-Objects) (And-Statement in SQL).
     * @param rootEntity Root Used to determine the Object-Path for the Object properties that are compared to the ones in the beanFilter, so that they can be written in the Criteria.
     * @return list
     */
    public abstract List<Predicate> whereClause(FilterType beanFilter, CriteriaBuilder cb, Root<EntityType> rootEntity);

    /**
     * @param beanFilter Contains a SortingField object, in which the Entity-Property is written by which the query should be ordered by, and a boolean that indicates which way should be ordered
     * @param cb         CriteriaBuilder-Object which creates the order parameters (so called Order-Object).
     * @param rootEntity Root Used to determine the Object-Path for the Object properties, so that they can be written in the Criteria.
     * @return List
     */
    public abstract List<Order> orderByClause(FilterType beanFilter, CriteriaBuilder cb, Root<EntityType> rootEntity);
}
