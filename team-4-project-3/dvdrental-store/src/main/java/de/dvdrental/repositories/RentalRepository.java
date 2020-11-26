package de.dvdrental.repositories;

import de.dvdrental.entities.Rental;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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

    public List<Rental> getRentalsOfCustomer(Integer customerId) {
        TypedQuery<Rental> q = em.createQuery("select r from Rental r where r.customerId = :customerId", Rental.class);
        q.setParameter("customerId", customerId);
        return q.getResultList();
    }

    public List<Rental> getAll(Integer limit, Integer offset) {
        return em.createQuery("select r from Rental r order by r.rentalId desc", Rental.class)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public List<Rental> searchAll(String search, Integer limit, Integer offset) {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rental> q = cb.createQuery(Rental.class);
        Root<Rental> r = q.from(Rental.class);
        Predicate likeCustomerId = cb.like(cb.upper(r.get("customerId").as(String.class)), search.toUpperCase() + "%");
        Predicate likeId = cb.like(cb.upper(r.get("rentalId").as(String.class)), search.toUpperCase() + "%");
        predicates.add(cb.or(likeId, likeCustomerId));
        q.select(r)
                .where(predicates.toArray(new Predicate[]{}))
                .orderBy(cb.desc(r.get("rentalId")));
        return em.createQuery(q).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
