package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Film;
import de.dvdrental.entities.FilmCategory;
import de.dvdrental.jsfBeans.filter.FilmBeanFilter;
import de.dvdrental.jsfBeans.filter.SortingField;
import de.dvdrental.jsfBeans.interfaces.Pagination;
import de.dvdrental.repositories.FilmCategoryRepository;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class FilmBean extends Pagination<FilmCategory, FilmBeanFilter, FilmCategoryRepository> implements Serializable {

    @Inject
    protected FilmCategoryRepository filmCategoryRepo;
    @Inject
    protected FilmBeanFilter filter;

    private SortingField idSort;
    private SortingField categorySort;
    private SortingField titleSort;
    private SortingField releaseYearSort;
    private SortingField lengthSort;
    private SortingField ratingSort;
    private SortingField rentalDurationSort;
    private SortingField rentalRateSort;
    private SortingField replacementCostSort;

    protected FilmBean() {
        super(50);
    }

    @PostConstruct
    public void init() {
        //these properties are set as filter in the ActionListeners of the CommandLinks (which work as table headers)
        idSort = new SortingField("id");
        categorySort = new SortingField("category");
        titleSort = new SortingField("title");
        releaseYearSort = new SortingField("releaseYear");
        lengthSort = new SortingField("length");
        ratingSort = new SortingField("rating");
        rentalDurationSort = new SortingField("rentalDuration");
        rentalRateSort = new SortingField("rentalRate");
        replacementCostSort = new SortingField("replacementCost");
        idSort.setAsc(true);
        filter.setSortingField(idSort);
        init(filmCategoryRepo, filter);
        idSort.toggleSortAsc();
    }
    //Toggles the Sort-Ascending boolean value of the SortingField

    // Create data binding for select tags - Create and Fill an Array of SelectItem Objects with either the Film.Rating enum or Category Objects
    public Object[] getRatings() {
        SelectItem[] items = new SelectItem[Film.Rating.values().length];
        for (int i = 0; i < items.length; i++) {
            Film.Rating rating = Film.Rating.values()[i];
            items[i] = new SelectItem(rating, rating.toString());
        }
        return items;
    }

    public FilmBeanFilter getFilter() {
        return filter;
    }

    public void setFilter(FilmBeanFilter filter) {
        this.filter = filter;
    }

    public SortingField getIdSort() {
        return idSort;
    }

    public void setIdSort(SortingField idSort) {
        this.idSort = idSort;
    }

    public SortingField getCategorySort() {
        return categorySort;
    }

    public void setCategorySort(SortingField categorySort) {
        this.categorySort = categorySort;
    }

    public SortingField getTitleSort() {
        return titleSort;
    }

    public void setTitleSort(SortingField titleSort) {
        this.titleSort = titleSort;
    }

    public SortingField getReleaseYearSort() {
        return releaseYearSort;
    }

    public void setReleaseYearSort(SortingField releaseYearSort) {
        this.releaseYearSort = releaseYearSort;
    }

    public SortingField getLengthSort() {
        return lengthSort;
    }

    public void setLengthSort(SortingField lengthSort) {
        this.lengthSort = lengthSort;
    }

    public SortingField getRatingSort() {
        return ratingSort;
    }

    public void setRatingSort(SortingField ratingSort) {
        this.ratingSort = ratingSort;
    }

    public SortingField getRentalDurationSort() {
        return rentalDurationSort;
    }

    public void setRentalDurationSort(SortingField rentalDurationSort) {
        this.rentalDurationSort = rentalDurationSort;
    }

    public SortingField getRentalRateSort() {
        return rentalRateSort;
    }

    public void setRentalRateSort(SortingField rentalRateSort) {
        this.rentalRateSort = rentalRateSort;
    }

    public SortingField getReplacementCostSort() {
        return replacementCostSort;
    }

    public void setReplacementCostSort(SortingField replacementCostSort) {
        this.replacementCostSort = replacementCostSort;
    }

}
