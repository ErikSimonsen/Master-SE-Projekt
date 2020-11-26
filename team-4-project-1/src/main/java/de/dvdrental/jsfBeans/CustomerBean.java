package de.dvdrental.jsfBeans;

import de.dvdrental.entities.views.CustomerList;
import de.dvdrental.jsfBeans.filter.CustomerBeanFilter;
import de.dvdrental.jsfBeans.filter.SortingField;
import de.dvdrental.jsfBeans.interfaces.Pagination;
import de.dvdrental.repositories.views.CustomerListRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class CustomerBean extends Pagination<CustomerList, CustomerBeanFilter, CustomerListRepository> implements Serializable {

    @Inject
    CustomerListRepository customerListRepository;
    @Inject
    CustomerBeanFilter filter;

    private SortingField idSort;
    private SortingField nameSort;
    private SortingField addressSort;
    private SortingField zipSort;
    private SortingField phoneSort;
    private SortingField citySort;
    private SortingField countrySort;
    private SortingField notesSort;
    private SortingField sidSort;

    public CustomerBean() {
        super(50);
    }

    @PostConstruct
    public void init() {
        init(customerListRepository, filter);

        idSort = new SortingField("id");
        nameSort = new SortingField("name");
        addressSort = new SortingField("address");
        zipSort = new SortingField("zipCode");
        phoneSort = new SortingField("phone");
        citySort = new SortingField("city");
        countrySort = new SortingField("country");
        notesSort = new SortingField("notes");
        sidSort = new SortingField("sid");

        idSort.setAsc(true);
        filter.setSortingField(idSort);
    }

    //Getter and Setter
    public SortingField getIdSort() {
        return idSort;
    }

    public void setIdSort(SortingField idSort) {
        this.idSort = idSort;
    }

    public SortingField getNameSort() {
        return nameSort;
    }

    public void setNameSort(SortingField titleSort) {
        this.nameSort = titleSort;
    }

    public SortingField getAddressSort() {
        return addressSort;
    }

    public void setAddressSort(SortingField addressSort) {
        this.addressSort = addressSort;
    }

    public SortingField getNotesSort() {
        return notesSort;
    }

    public void setNotesSort(SortingField notesSort) {
        this.notesSort = notesSort;
    }

    public SortingField getZipSort() {
        return zipSort;
    }

    public void setZipSort(SortingField zipSort) {
        this.zipSort = zipSort;
    }

    public SortingField getPhoneSort() {
        return phoneSort;
    }

    public void setPhoneSort(SortingField phoneSort) {
        this.phoneSort = phoneSort;
    }

    public SortingField getCitySort() {
        return citySort;
    }

    public void setCitySort(SortingField citySort) {
        this.citySort = citySort;
    }

    public SortingField getCountrySort() {
        return countrySort;
    }

    public void setCountrySort(SortingField countrySort) {
        this.countrySort = countrySort;
    }

    public SortingField getSidSort() {
        return sidSort;
    }

    public void setSidSort(SortingField sidSort) {
        this.sidSort = sidSort;
    }

    public CustomerBeanFilter getFilter() {
        return filter;
    }

    public void setFilter(CustomerBeanFilter filter) {
        this.filter = filter;
    }
}
