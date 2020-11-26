package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Staff;
import de.dvdrental.repositories.AddressRepository;
import de.dvdrental.repositories.StaffRepository;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EditStaffBean implements Serializable {
    @Inject
    StaffRepository staffRepository;
    @Inject
    AddressRepository addressRepository;

    private int urlStaffId;
    private Staff staff;

    public void loadStaff() {
        staff = staffRepository.get(urlStaffId);
    }

    public String update() {
        addressRepository.update(staff.getAddress());
        staffRepository.update(staff);
        return "staff?faces-redirect=true";
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public int getUrlStaffId() {
        return urlStaffId;
    }

    public void setUrlStaffId(int staffId) {
        this.urlStaffId = staffId;
    }
}
