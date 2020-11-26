package de.dvdrental.repositories;

import de.dvdrental.entities.Film;
import de.dvdrental.entities.Inventory;
import de.dvdrental.entities.Store;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Named
@Stateless
public class InventoryRepository extends CrudRepository<Inventory> {
    public InventoryRepository() {
        super(Inventory.class);
    }

    public long getStockNumber(Film film, Store store) {
        Query query = em.createQuery("select count(i) from Inventory i where i.film = :film and i.store = :store");
        query.setParameter("film", film);
        query.setParameter("store", store);
        return (long) query.getSingleResult();
    }

    public long getNumberOfRents(Film film, Store store) {
        Query query = em.createQuery("select count(i.film) from Inventory i inner join Rental r On r.inventory = i Where r.returnDate is NULL and i.store = :store and i.film = :film");
        query.setParameter("store", store);
        query.setParameter("film", film);
        return (long) query.getSingleResult();
    }

    //TODO: Create Java Doc!
    public List<Inventory> getFreeInventory(Film film, Store store) {
        TypedQuery<Inventory> query = em.createQuery(
                "select i from Inventory i left join Rental r On r.inventory = i " +
                "where i.film = :film and i.store = :store and (r.rentalDate is NULL or" +
                "(select MAX(r2.rentalDate) from Rental r2 where r2.inventory = r.inventory) = r.rentalDate " +
                "and r.returnDate is not NULL) order by i.inventoryId", Inventory.class);

        query.setParameter("film", film);
        query.setParameter("store", store);

        return query.getResultList();
    }
}
