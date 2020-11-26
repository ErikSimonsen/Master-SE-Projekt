package de.dvdrental.repositories;

import de.dvdrental.entities.Address;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AddressRepository extends CrudRepository<Address> {
    public AddressRepository() {
        super(Address.class);
    }
}
