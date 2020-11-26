package de.dvdrental.jsfBeans.filmRental;

import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Payment;
import de.dvdrental.entities.Rental;
import de.dvdrental.jsfBeans.SessionBean;
import de.dvdrental.repositories.CustomerRepository;
import de.dvdrental.repositories.PaymentRepository;
import de.dvdrental.repositories.RentalRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PaymentBean implements Serializable {
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private RentalRepository rentalRepository;
    @Inject
    private PaymentRepository paymentRepository;
    @Inject
    private SessionBean sessionBean;

    private Integer customerIdUrlParameter;
    private String rentalIdsUrlParameter;
    private Customer customer;
    private List<Rental> rentals;
    private boolean hasPaid;

    @PostConstruct
    public void init() {
        hasPaid = false;
    }

    public void loadUrlParameters() {
        customer = customerRepository.get(customerIdUrlParameter);
        rentals = new ArrayList<>();
        for (String id: rentalIdsUrlParameter.split(",")) {
            rentals.add(rentalRepository.get(Integer.valueOf(id)));
        }
    }

    public BigDecimal getCosts() {
        return rentals.stream()
                .map(rental -> rental.getInventory().getFilm().getRentalRate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getPaymentButtonText() {
        if (!hasPaid)
            return "Bezahlung durchführen";
        return "Bezahlung durchgeführt";
    }

    public void pay() {
        if (!hasPaid) {
            for (Rental r: rentals) {
                BigDecimal amount = r.getInventory().getFilm().getRentalRate();
                Payment p = new Payment(amount, customer, sessionBean.getActiveStaff(), r);
                paymentRepository.create(p);
            }
            hasPaid = true;
        }
    }

    //Getter and Setter
    public Integer getCustomerIdUrlParameter() {
        return customerIdUrlParameter;
    }

    public void setCustomerIdUrlParameter(Integer customerIdUrlParameter) {
        this.customerIdUrlParameter = customerIdUrlParameter;
    }

    public String getRentalIdsUrlParameter() {
        return rentalIdsUrlParameter;
    }

    public void setRentalIdsUrlParameter(String rentalIdsUrlParameter) {
        this.rentalIdsUrlParameter = rentalIdsUrlParameter;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }
}