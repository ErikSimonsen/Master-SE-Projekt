package de.dvdrental.repositories;

import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Payment;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    public List<Customer> getAll(Integer limit, Integer offset) {
        return em.createQuery("Select c from Customer c order by c.customerId", Customer.class)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public List<Customer> searchAll(String search, Integer limit, Integer offset) {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> q = cb.createQuery(Customer.class);
        Root<Customer> c = q.from(Customer.class);
        Predicate likeId = cb.like(cb.upper(c.get("customerId").as(String.class)), search.toUpperCase() + "%");
        Predicate likeFirstName = cb.like(cb.upper(c.get("firstName")), search.toUpperCase() + "%");
        Predicate likeLastName = cb.like(cb.upper(c.get("lastName")), search.toUpperCase() + "%");
        predicates.add(cb.or(likeId, likeFirstName, likeLastName));
        q.select(c)
                .where(predicates.toArray(new Predicate[]{})).orderBy(cb.asc(c.get("customerId")));
        return em.createQuery(q).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
