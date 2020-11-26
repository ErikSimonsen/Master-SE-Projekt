package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Customer;
import de.dvdrental.repositories.AddressRepository;
import de.dvdrental.repositories.CustomerRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;

@Named
@ViewScoped
public class AddCustomerBean implements Serializable {
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private AddressRepository addressRepository;
    @Inject
    private SessionBean sessionBean;

    private Customer customer;

    public AddCustomerBean() {
    }

    @PostConstruct
    private void init() {
        customer = new Customer();
    }

    public String saveCustomer() {
        customer.setActive(1);
        customer.setActivebool(true);
        customer.setCreateDate(new Timestamp(System.currentTimeMillis()));
        customer.setStore(sessionBean.getActiveStaff().getStore());
        addressRepository.create(customer.getAddress());
        customerRepository.create(customer);
        return "customers?faces-redirect=true";
    }

    //Getter and setter
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
