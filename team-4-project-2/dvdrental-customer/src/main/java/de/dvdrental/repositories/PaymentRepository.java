package de.dvdrental.repositories;

import de.dvdrental.entities.Payment;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
public class PaymentRepository extends CrudRepository<Payment> {
    public PaymentRepository() {
        super(Payment.class);
    }
}
