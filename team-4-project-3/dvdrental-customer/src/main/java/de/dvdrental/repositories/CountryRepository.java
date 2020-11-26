package de.dvdrental.repositories;

import de.dvdrental.entities.Country;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import java.util.List;

@RequestScoped
public class CountryRepository extends CrudRepository<Country> {
    public CountryRepository() {
        super(Country.class);
    }

    public Country getCountryByName(String name) {
        TypedQuery<Country> q = em.createQuery("select c from Country c where c.country = :name", Country.class);
        q.setParameter("name", name);
        List<Country> countries = q.getResultList();
        if (!countries.isEmpty())
            return countries.get(0);
        return null;
    }
}
