package de.dvdrental.repositories;

import de.dvdrental.entities.City;
import de.dvdrental.entities.Country;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import java.util.List;

@RequestScoped
public class CityRepository extends CrudRepository<City> {
    public CityRepository() {
        super(City.class);
    }

    public City getCityByNameAndCountry(String name, Country country) {
        TypedQuery<City> q = em.createQuery("select c from City c where c.city = :name and c.country = :country", City.class);
        q.setParameter("name", name);
        q.setParameter("country", country);
        List<City> cities = q.getResultList();
        if (!cities.isEmpty())
            return cities.get(0);
        return null;
    }
}

