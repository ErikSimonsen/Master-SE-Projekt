package de.dvdrental.microservices;

import de.dvdrental.entities.InventoryStatus;
import de.dvdrental.entities.Rental;
import de.dvdrental.entities.Staff;
import de.dvdrental.entities.Store;
import de.dvdrental.microservices.interfaces.MicroserviceClient;
import de.dvdrental.microservices.proxies.RentalPostProxy;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Named
@RequestScoped
public class StoreClient extends MicroserviceClient {
    public StoreClient() {
        super(System.getenv("STORE_MICROSERVICE_ADDRESS"));//super("http://store_microservice:8086");
    }

    public List<Rental> getAllRentals(Integer limit, Integer offset) {
        return get(targetURI + "/rentals", new GenericType<>() {}, Map.of("limit", limit, "offset", offset));
    }

    public List<Rental> searchRentals(String search, Integer limit, Integer offset) {
        return get(
                targetURI + "/rentals/search/" + search,
                new GenericType<>() {},
                Map.of("limit", limit, "offset", offset));
    }
    public Long countRentals() {
        return get(targetURI + "/rentals/count", new GenericType<>() {});
    }
    public Staff getStaff(Integer id) {
        return get(targetURI + "/staff/" + id, new GenericType<>() {});
    }

    public List<Staff> getAllStaffs() {
        return get(targetURI + "/staff", new GenericType<>() {});
    }

    public List<Rental> getRentalsOfCustomer(Integer customerId) {
        return get(targetURI + "/rentals/customer/" + customerId, new GenericType<>() {});
    }

    public void returnRental(Rental r) {
        put(String.format("%s/rentals/%s/returned", targetURI, r.getRentalId()));
    }

    public List<Store> getAllStores() {
        return get(targetURI + "/stores", new GenericType<>() {});
    }

    public void createInventory(Integer filmId, Integer storeId) {
        post(targetURI + "/inventories", null, Map.of("filmId", filmId, "storeId", storeId));
    }

    public InventoryStatus getInventoryStatus(Integer filmId, Integer storeId) {
        return get(String.format("%s/inventories/film/%s/store/%s", targetURI, filmId, storeId), new GenericType<>() {});
    }

    public Integer createRental(Integer customerId, Date date, Integer inventoryId, Integer staffId) {
        RentalPostProxy proxy = new RentalPostProxy(inventoryId, customerId, staffId, date);
        return post(targetURI + "/rentals", proxy);
    }

    public Rental getRental(Integer id) {
        return get(targetURI + "/rentals/" + id, new GenericType<>() {});
    }
}
