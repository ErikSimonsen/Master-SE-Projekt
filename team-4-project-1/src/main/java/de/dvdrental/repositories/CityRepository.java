package de.dvdrental.repositories;

import de.dvdrental.entities.City;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class CityRepository extends CrudRepository<City> {
    public CityRepository() {
        super(City.class);
    }
}

