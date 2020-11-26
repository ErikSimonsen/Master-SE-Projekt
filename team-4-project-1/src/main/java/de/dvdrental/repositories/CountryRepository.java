package de.dvdrental.repositories;

import de.dvdrental.entities.Country;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class CountryRepository extends CrudRepository<Country> {
    public CountryRepository() {
        super(Country.class);
    }
}
