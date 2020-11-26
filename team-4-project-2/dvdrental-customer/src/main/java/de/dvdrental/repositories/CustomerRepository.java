package de.dvdrental.repositories;

import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Payment;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@RequestScoped
public class CustomerRepository extends CrudRepository<Customer> {
    public CustomerRepository() {
        super(Customer.class);
    }


    public List<Payment> getPayments(Customer customer) {
        TypedQuery<Payment> query = em.createQuery("select p from Payment p where p.customer = :customer", Payment.class);
        query.setParameter("customer", customer);
        return query.getResultList();
    }
}
