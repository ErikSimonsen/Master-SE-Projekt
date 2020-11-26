package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Film;
import de.dvdrental.entities.Payment;
import de.dvdrental.entities.Rental;
import de.dvdrental.repositories.CustomerRepository;
import de.dvdrental.repositories.PaymentRepository;
import de.dvdrental.repositories.RentalRepository;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Named
@ViewScoped
public class CustomerRentalsBean implements Serializable {
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private SessionBean sessionBean;
    @Inject
    private CustomerRentalsService customerRentalsService;
    @Inject
    private PaymentRepository paymentRepository;
    @Inject
    private RentalRepository rentalRepository;

    private Integer customerIdUrlParameter;
    private Customer customer;
    private List<Rental> rentals;

    public CustomerRentalsBean() {
    }

    public void loadUrlParameters() {
        customer = customerRepository.get(customerIdUrlParameter);
        rentals = customerRepository.getRentals(customer);
    }

    public String getActionButtonText(Rental rental) {
        if (rental.getReturnDate() == null)
            return "Film zurückgeben";
        if (!customerRentalsService.isPaid(rental))
            return "Betrag begleichen";
        return "";
    }

    public String returnFilmAndPayCosts(Rental rental) {
        if (rental.getReturnDate() == null) {
            rental.setReturnDate(new Timestamp(System.currentTimeMillis()));
            rentalRepository.update(rental);
        }
        if (!customerRentalsService.isPaid(rental)) {
            BigDecimal amount = customerRentalsService.getCostsLeftToPay(rental);
            Payment p = new Payment(amount, customer, sessionBean.getActiveStaff(), rental);
            paymentRepository.create(p);
        }
        return "";
    }

    //Getter and setter
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getCustomerIdUrlParameter() {
        return customerIdUrlParameter;
    }

    public void setCustomerIdUrlParameter(Integer customerIdUrlParameter) {
        this.customerIdUrlParameter = customerIdUrlParameter;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}
