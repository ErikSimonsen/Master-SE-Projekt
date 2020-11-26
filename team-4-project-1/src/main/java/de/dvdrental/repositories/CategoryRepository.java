package de.dvdrental.repositories;

import de.dvdrental.entities.Category;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class CategoryRepository extends CrudRepository<Category> {
    public CategoryRepository() {
        super(Category.class);
    }
}
