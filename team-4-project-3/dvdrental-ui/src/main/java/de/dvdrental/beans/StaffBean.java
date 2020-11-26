package de.dvdrental.beans;

import de.dvdrental.entities.Staff;
import de.dvdrental.microservices.StoreClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class StaffBean implements Serializable {
    @Inject
    private StoreClient storeClient;

    private List<Staff> staffs;

    public StaffBean() {
    }

    @PostConstruct
    public void init() {
        staffs = storeClient.getAllStaffs();
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

}
