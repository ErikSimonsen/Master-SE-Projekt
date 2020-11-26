package de.dvdrental.beans;

import de.dvdrental.entities.Address;
import de.dvdrental.entities.City;
import de.dvdrental.entities.Customer;
import de.dvdrental.microservices.CustomerClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class AddCustomerBean implements Serializable {
    @Inject
    private CustomerClient customerClient;
    @Inject
    private SessionBean sessionBean;

    private Customer customer;
    private Address address;
    private City city;

    public AddCustomerBean() {
    }

    @PostConstruct
    private void init() {
        customer = new Customer();
        address = new Address();
        city = new City();
    }

    public String saveCustomer() {
        address.setCity(city.getCity());
        address.setCountry(city.getCounty());
        Integer addressId = customerClient.createAddress(address);

        customer.setActive(1);
        customer.setActivebool(true);
        customerClient.createCustomer(customer, addressId, sessionBean.getActiveStaff().getStoreId());
        return "customers?faces-redirect=true";
    }

    //Getter and setter
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
