package de.dvdrental.repositories;

import de.dvdrental.entities.Rental;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

@RequestScoped
public class RentalRepository extends CrudRepository<Rental> {
    public RentalRepository() {
        super(Rental.class);
    }

    public boolean checkIfViolatesConstraint(Rental rental) {
        Query query = em.createQuery("select count(r) from Rental r where r.customerId = :customerId and r.inventory = :inventory and r.rentalDate = :rentalDate");
        query.setParameter("customerId", rental.getCustomerId());
        query.setParameter("inventory", rental.getInventory());
        query.setParameter("rentalDate", rental.getRentalDate());
        long n = (long) query.getSingleResult();
        return n > 0;
    }
}
