package de.dvdrental.repositories;

import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Rental;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Named
@Stateless
public class CustomerRepository extends CrudRepository<Customer> {
    public CustomerRepository() {
        super(Customer.class);
    }

    public List<Rental> getRentals(Customer customer) {
        TypedQuery<Rental> q = em.createQuery("select r from Rental r where r.customer = :customer", Rental.class);
        q.setParameter("customer", customer);
        return q.getResultList();
    }

    public long getNumRentals(Customer c) {
        Query query = em.createQuery("select count(r) from Rental r where r.customer = :customer");
        query.setParameter("customer", c);
        return (long) query.getSingleResult();
    }

    public long getNumOpenRentals(Customer c) {
        Query query = em.createQuery("select count(r) from Rental r where r.customer=:customer and r.returnDate is null");
        query.setParameter("customer", c);
        return (long) query.getSingleResult();
    }

    public boolean hasAnyRentals(Customer c) {
        return getNumRentals(c) > 0;
    }

    public boolean hasOpenRental(Customer c) {
        return getNumOpenRentals(c) > 0;
    }
}
