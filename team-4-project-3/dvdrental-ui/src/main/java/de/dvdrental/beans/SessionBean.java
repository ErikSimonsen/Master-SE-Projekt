package de.dvdrental.beans;

import de.dvdrental.entities.Staff;
import de.dvdrental.microservices.CustomerClient;
import de.dvdrental.microservices.FilmClient;
import de.dvdrental.microservices.StoreClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@SessionScoped
public class SessionBean implements Serializable {

    @Inject private CustomerClient customerClient;
    @Inject private FilmClient filmClient;
    @Inject private StoreClient storeClient;

    private Boolean isCustomerClientAvailable;
    private Boolean isStoreClientAvailable;
    private Boolean isFilmClientAvailable;

    @PostConstruct
    public void init() {
        isCustomerClientAvailable = customerClient.testTargetService();
        isStoreClientAvailable = storeClient.testTargetService();
        isFilmClientAvailable = filmClient.testTargetService();
    }

    private Staff activeStaff;

    public void logOut() {
        activeStaff = null;
    }

    public boolean isLoggedIn() {
        return activeStaff != null;
    }

    public void setActiveStaff(Staff staff) {
        activeStaff = staff;
    }

    public Staff getActiveStaff() {
        return activeStaff;
    }

    public String checkForLogin() {
        if (!isLoggedIn()) {
            return "login?faces-redirect=true";
        }
        return null;
    }

    public Boolean getCustomerClientAvailable() {
        return isCustomerClientAvailable;
    }

    public Boolean getStoreClientAvailable() {
        return isStoreClientAvailable;
    }

    public Boolean getFilmClientAvailable() {
        return isFilmClientAvailable;
    }
}
