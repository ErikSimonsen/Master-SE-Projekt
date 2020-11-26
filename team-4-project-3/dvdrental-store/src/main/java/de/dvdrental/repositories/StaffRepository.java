package de.dvdrental.repositories;

import de.dvdrental.entities.Staff;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class StaffRepository extends CrudRepository<Staff> {
    public StaffRepository() {
        super(Staff.class);
    }
}
