package de.dvdrental.beans.filter;

import java.util.Comparator;

public class SortingField<T> {
    private Boolean ascending = false;

    private Comparator<T> comparator;

    public SortingField(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void toggle() {
        ascending = !ascending;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Boolean getAscending() {
        return ascending;
    }

    public void setAscending(Boolean ascending) {
        this.ascending = ascending;
    }
}
