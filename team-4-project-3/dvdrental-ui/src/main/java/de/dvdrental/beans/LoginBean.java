package de.dvdrental.beans;

import de.dvdrental.entities.Staff;
import de.dvdrental.microservices.CustomerClient;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class LoginBean implements Serializable {
    @Inject
    SessionBean sessionBean;

    Staff selectedStaff;

    /**
     * Needed so that a logged in user does not have to login again. instead he gets navigated to the index page.
     */
    public String checkStaffSession() {
        if (sessionBean.isLoggedIn()) {
            return getStartPage();
        }
        return null;
    }

    public String logIn() {
        sessionBean.setActiveStaff(selectedStaff);
        return getStartPage();
    }

    public String getStartPage() {
        if (sessionBean.getCustomerClientAvailable())
            return "customers?faces-redirect=true";
        return "staffs?faces-redirect=true";
    }

    public Staff getSelectedStaff() {
        return selectedStaff;
    }

    public void setSelectedStaff(Staff selectedStaff) {
        this.selectedStaff = selectedStaff;
    }
}
