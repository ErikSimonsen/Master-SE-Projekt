package de.dvdrental.jsfBeans.interfaces;

import de.dvdrental.jsfBeans.filter.SortingField;

import java.io.Serializable;

/**
 * This only exists, to safely know that each JSF-Bean Filter class has a sorting field, as this is needed in the Repository Classes that implement CrudWithFilterRepository
 */
public abstract class BeanFilter implements Serializable {

    private SortingField sortingField;

    public SortingField getSortingField() {
        return sortingField;
    }

    public void setSortingField(SortingField sortingField) {
        this.sortingField = sortingField;
    }
}
