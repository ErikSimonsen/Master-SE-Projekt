package de.dvdrental.repositories;

import de.dvdrental.entities.Category;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class CategoryRepository extends CrudRepository<Category> {
    public CategoryRepository() {
        super(Category.class);
    }

    public Category getByName(String categoryName) {
        List<Category> matches = em.createQuery("select c from Category c where UPPER(c.name) = UPPER(:name)", Category.class)
                                   .setParameter("name", categoryName).getResultList();
        if (matches.isEmpty())
            return null;
        return matches.get(0);
    }
}
