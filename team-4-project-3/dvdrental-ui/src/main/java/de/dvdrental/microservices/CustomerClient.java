package de.dvdrental.microservices;

import de.dvdrental.entities.Address;
import de.dvdrental.entities.City;
import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Rental;
import de.dvdrental.microservices.interfaces.MicroserviceClient;
import de.dvdrental.microservices.proxies.PaymentPostProxy;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class CustomerClient extends MicroserviceClient {
    public CustomerClient() {
         super(System.getenv("CUSTOMER_MICROSERVICE_ADDRESS")); //super("http://customer_microservice:8084");
    }

    public List<Customer> getAllCustomers(Integer limit, Integer offset) {
        return get(
                targetURI + "/customers",
                new GenericType<>() {},
                Map.of("limit", limit, "offset", offset));
    }

    public List<Customer> searchCustomers(String search, Integer limit, Integer offset) {
        return get(
                targetURI + "/customers/search/" + search,
                new GenericType<>() {},
                Map.of("limit", limit, "offset", offset));
    }
    public List<City> getAllCities() {
        return get(targetURI + "/city", new GenericType<>() {});
    }

    public City getCity(Integer id) {
        return get(targetURI + "/city/" + id, new GenericType<>() {});
    }

    public Integer getNumCustomers() {
        return get(targetURI + "/customers/count", new GenericType<>() {});
    }

    public Customer getCustomer(Integer id) {
        return get(targetURI + "/customers/" + id, new GenericType<>() {});
    }

    public Customer getCustomerOfRental(Rental r) {
        return get(r.getCustomer().getHref(), new GenericType<>() {});
    }

    public BigDecimal getSumPaymentsOfRental(Rental r) {
        return get(targetURI + "/payments/sum/rental/" + r.getRentalId(), new GenericType<>() {});
    }

    public void createPayment(BigDecimal amount, Integer customerId, Integer rentalId, Integer staffId) {
        PaymentPostProxy paymentPostProxy = new PaymentPostProxy(amount, customerId, new Date(System.currentTimeMillis()), rentalId, staffId);
        post(targetURI + "/payments", paymentPostProxy);
    }

    public Integer createAddress(Address address) {
        return post(targetURI + "/addresses", address);
    }

    public Address getAddressFromCustomer(Customer customer) {
        return get(customer.getAddress().getHref(), new GenericType<>() {});
    }

    public void createCustomer(Customer customer, Integer addressId, Integer storeId) {
        post(targetURI + "/customers", customer, Map.of("address", addressId, "store", storeId));
    }
}
