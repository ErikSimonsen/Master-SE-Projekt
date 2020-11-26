package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Staff;
import de.dvdrental.entities.Store;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;


/**
 * Manages the logged in user
 */
@Named
@SessionScoped
public class SessionBean implements Serializable {
    private Staff activeStaff;

    public void logIn(Staff staff) {
        activeStaff = staff;
    }

    public void logOut() {
        activeStaff = null;
    }

    public boolean isLoggedIn() {
        return activeStaff != null;
    }

    public Staff getActiveStaff() {
        return activeStaff;
    }

    public String checkForStoreOwner() {
        Store store = activeStaff.getStore();
        Staff storeManager = store.getStaff();
        if (storeManager.getStaffId() != activeStaff.getStaffId()) {
            return "index?faces-redirect=true";
        }
        return null;
    }

    public String checkForLogin() {
        if (!isLoggedIn()) {
            return "login?faces-redirect=true";
        }
        return null;
    }
}
