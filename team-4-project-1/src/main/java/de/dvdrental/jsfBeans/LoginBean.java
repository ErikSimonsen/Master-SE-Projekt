package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Staff;
import de.dvdrental.repositories.StaffRepository;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class LoginBean implements Serializable {
    @Inject
    StaffRepository staffRepository;
    Staff selectedStaff;

    @Inject
    SessionBean staffSession;

    /**
     * Needed so that a logged in user does not have to login again. instead he gets navigated to the index page.
     *
     * @return
     */
    public String checkStaffSession() {
        if (staffSession.isLoggedIn()) {
            return "/index?faces-redirect=true";
        }
        return null;
    }


    public Staff getSelectedStaff() {
        return selectedStaff;
    }

    public void setSelectedStaff(Staff selectedStaff) {
        this.selectedStaff = selectedStaff;
    }

    public List<Staff> getStaffList() {
        return staffRepository.getAll();
    }

}
