package de.dvdrental.repositories;

import de.dvdrental.entities.Store;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class StoreRepository extends CrudRepository<Store> {
    public StoreRepository() {
        super(Store.class);
    }
}
