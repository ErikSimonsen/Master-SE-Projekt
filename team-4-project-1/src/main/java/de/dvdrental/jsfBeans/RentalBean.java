package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Rental;
import de.dvdrental.jsfBeans.filter.RentalBeanFilter;
import de.dvdrental.jsfBeans.filter.SortingField;
import de.dvdrental.jsfBeans.interfaces.Pagination;
import de.dvdrental.repositories.RentalRepository;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class RentalBean extends Pagination<Rental, RentalBeanFilter, RentalRepository> implements Serializable {
    @Inject
    RentalRepository rentalRepository;
    @Inject
    RentalBeanFilter filter;

    private SortingField idSort;
    private SortingField customerIdSort;
    private SortingField filmNameSort;
    private SortingField rentalPriceSort;
    private SortingField filmIdSort;
    private SortingField rentalDateSort;
    private SortingField returnDateSort;

    public RentalBean() {
        super(50);
    }

    @PostConstruct
    public void init() {
        idSort = new SortingField("id");
        customerIdSort = new SortingField("customerId");
        filmIdSort = new SortingField("filmId");
        filmNameSort = new SortingField("filmName");
        rentalPriceSort = new SortingField("rentalPrice");
        rentalDateSort = new SortingField("rentalDate");
        returnDateSort = new SortingField("returnDate");
        //initially sort by id desc, because first rentals are not relevant
        idSort.setAsc(false);
        filter.setSortingField(idSort);

        init(rentalRepository, filter);
        idSort.toggleSortAsc();
    }

    // Create data binding for select tags - Create and Fill an Array of SelectItem Objects with either the Film.Rating enum or Category Objects
    public Object[] getReturned() {
        SelectItem[] items = new SelectItem[RentalBeanFilter.Returned.values().length];
        for (int i = 0; i < items.length; i++) {
            RentalBeanFilter.Returned returned = RentalBeanFilter.Returned.values()[i];
            items[i] = new SelectItem(returned, returned.getShortName());
        }
        return items;
    }

    public RentalBeanFilter getFilter() {
        return filter;
    }

    public void setFilter(RentalBeanFilter filter) {
        this.filter = filter;
    }

    public SortingField getIdSort() {
        return idSort;
    }

    public void setIdSort(SortingField idSort) {
        this.idSort = idSort;
    }

    public SortingField getCustomerIdSort() {
        return customerIdSort;
    }

    public void setCustomerIdSort(SortingField customerIdSort) {
        this.customerIdSort = customerIdSort;
    }

    public SortingField getFilmNameSort() {
        return filmNameSort;
    }

    public void setFilmNameSort(SortingField filmNameSort) {
        this.filmNameSort = filmNameSort;
    }

    public SortingField getRentalPriceSort() {
        return rentalPriceSort;
    }

    public void setRentalPriceSort(SortingField rentalPriceSort) {
        this.rentalPriceSort = rentalPriceSort;
    }

    public SortingField getFilmIdSort() {
        return filmIdSort;
    }

    public void setFilmIdSort(SortingField filmIdSort) {
        this.filmIdSort = filmIdSort;
    }

    public SortingField getRentalDateSort() {
        return rentalDateSort;
    }

    public void setRentalDateSort(SortingField rentalDateSort) {
        this.rentalDateSort = rentalDateSort;
    }

    public SortingField getReturnDateSort() {
        return returnDateSort;
    }

    public void setReturnDateSort(SortingField returnDateSort) {
        this.returnDateSort = returnDateSort;
    }
}
