package de.dvdrental.repositories;

import de.dvdrental.entities.Store;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class StoreRepository extends CrudRepository<Store> {
    public StoreRepository() {
        super(Store.class);
    }
}
