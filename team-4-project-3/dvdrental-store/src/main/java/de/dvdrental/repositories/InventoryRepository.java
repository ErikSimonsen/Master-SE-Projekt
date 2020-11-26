package de.dvdrental.repositories;

import de.dvdrental.entities.Inventory;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@RequestScoped
public class InventoryRepository extends CrudRepository<Inventory> {
    public InventoryRepository() {
        super(Inventory.class);
    }

    public long getStockNumber(Integer filmId, Integer storeId) {
        Query query = em.createQuery("select count(i) from Inventory i where i.filmId = :filmId and i.storeId = :storeId");
        query.setParameter("filmId", filmId);
        query.setParameter("storeId", storeId);
        return (long) query.getSingleResult();
    }

    public long getNumberOfRents(Integer filmId, Integer storeId) {
        Query query = em.createQuery("select count(i) from Inventory i inner join Rental r On r.inventory = i Where r.returnDate is NULL and i.storeId = :storeId and i.filmId = :filmId");
        query.setParameter("storeId", storeId);
        query.setParameter("filmId", filmId);
        return (long) query.getSingleResult();
    }

    public List<Inventory> getFreeInventory(Integer filmId, Integer storeId) {
        TypedQuery<Inventory> query = em.createQuery(
                "select i from Inventory i left join Rental r On r.inventory = i " +
                        "where i.filmId = :filmId and i.storeId = :storeId and (r.rentalDate is NULL or" +
                        "(select MAX(r2.rentalDate) from Rental r2 where r2.inventory = r.inventory) = r.rentalDate " +
                        "and r.returnDate is not NULL) order by i.inventoryId", Inventory.class);

        query.setParameter("filmId", filmId);
        query.setParameter("storeId", storeId);

        return query.getResultList();
    }
}
