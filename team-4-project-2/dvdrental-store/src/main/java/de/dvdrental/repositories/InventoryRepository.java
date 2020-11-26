package de.dvdrental.repositories;

import de.dvdrental.entities.Inventory;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;


@RequestScoped
public class InventoryRepository extends CrudRepository<Inventory> {
    public InventoryRepository() {
        super(Inventory.class);
    }
}
