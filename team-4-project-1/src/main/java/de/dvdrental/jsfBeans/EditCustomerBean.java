package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Customer;
import de.dvdrental.repositories.AddressRepository;
import de.dvdrental.repositories.CustomerRepository;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EditCustomerBean implements Serializable {
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private AddressRepository addressRepository;

    private Integer customerIdUrlParameter;
    private Customer customer;

    public EditCustomerBean() {
    }

    public void loadUrlParameters() {
        customer = customerRepository.get(customerIdUrlParameter);
    }

    public String deleteCustomer() {
        customerRepository.delete(customer);
        return "customers?faces-redirect=true";
    }

    public String saveCustomer() {
        customer.setActive(customer.isActivebool() ? 1 : 0);
        addressRepository.update(customer.getAddress());
        customerRepository.update(customer);
        return "customers?faces-redirect=true";
    }

    //Getter and setter
    public Integer getCustomerIdUrlParameter() {
        return customerIdUrlParameter;
    }

    public void setCustomerIdUrlParameter(Integer customerIdUrlParameter) {
        this.customerIdUrlParameter = customerIdUrlParameter;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
