package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Staff;
import de.dvdrental.repositories.AddressRepository;
import de.dvdrental.repositories.StaffRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;

@Named
@ViewScoped
public class AddStaffBean implements Serializable {
    @Inject
    StaffRepository staffRepository;
    @Inject
    AddressRepository addressRepository;
    private Staff staff;
    private Part image;

    @PostConstruct
    public void init() {
        staff = new Staff();
    }

    public String save() {
        addressRepository.create(staff.getAddress());
        staffRepository.create(staff);
        return "staff?faces-redirect=true";
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }
}
