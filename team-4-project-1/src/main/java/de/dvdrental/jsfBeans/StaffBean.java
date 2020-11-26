package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Staff;
import de.dvdrental.jsfBeans.filter.SortingField;
import de.dvdrental.jsfBeans.filter.StaffBeanFilter;
import de.dvdrental.jsfBeans.interfaces.Pagination;
import de.dvdrental.repositories.StaffRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class StaffBean extends Pagination<Staff, StaffBeanFilter, StaffRepository> implements Serializable {
    @Inject
    StaffRepository staffRepository;

    @Inject
    StaffBeanFilter filter;

    SortingField idSort;
    SortingField firstNameSort;
    SortingField lastNameSort;
    SortingField emailSort;
    SortingField storeSort;


    SortingField statusSort;

    public StaffBean() {
        super(50);
    }

    @PostConstruct
    public void init() {
        init(staffRepository, filter);
        idSort = new SortingField("id");
        firstNameSort = new SortingField("firstName");
        lastNameSort = new SortingField("lastName");
        emailSort = new SortingField("email");
        storeSort = new SortingField("store");
        statusSort = new SortingField("status");
    }

    public void deactivateStaff(Staff staff) {
        staff.setActive(false);
        staffRepository.update(staff);
    }

    public SortingField getStatusSort() {
        return statusSort;
    }

    public void setStatusSort(SortingField statusSort) {
        this.statusSort = statusSort;
    }

    public StaffBeanFilter getFilter() {
        return filter;
    }

    public void setFilter(StaffBeanFilter filter) {
        this.filter = filter;
    }

    public SortingField getIdSort() {
        return idSort;
    }

    public void setIdSort(SortingField idSort) {
        this.idSort = idSort;
    }

    public SortingField getFirstNameSort() {
        return firstNameSort;
    }

    public void setFirstNameSort(SortingField firstNameSort) {
        this.firstNameSort = firstNameSort;
    }

    public SortingField getLastNameSort() {
        return lastNameSort;
    }

    public void setLastNameSort(SortingField lastNameSort) {
        this.lastNameSort = lastNameSort;
    }

    public SortingField getEmailSort() {
        return emailSort;
    }

    public void setEmailSort(SortingField emailSort) {
        this.emailSort = emailSort;
    }

    public SortingField getStoreSort() {
        return storeSort;
    }

    public void setStoreSort(SortingField storeSort) {
        this.storeSort = storeSort;
    }

}
