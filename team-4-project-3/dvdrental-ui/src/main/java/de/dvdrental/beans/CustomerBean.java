package de.dvdrental.beans;

import de.dvdrental.entities.Address;
import de.dvdrental.entities.Customer;
import de.dvdrental.microservices.CustomerClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Named
@ViewScoped
public class CustomerBean implements Serializable {
    @Inject
    CustomerClient customerClient;

    private final Integer NUM_ENTRIES_ON_PAGE = 50;

    private List<Customer> customers;
    private Map<Customer, Address> addresses;
    private List<String> pages;
    private Integer currentPage = 1;
    private String search;
    public CustomerBean() {
    }

    @PostConstruct
    public void init() {
        int numPages = (int) Math.ceil(customerClient.getNumCustomers().doubleValue() / NUM_ENTRIES_ON_PAGE);
        pages = IntStream.rangeClosed(1, numPages).mapToObj(String::valueOf).collect(Collectors.toList());
        refreshCustomers();
    }

    public void onSearchChange() {
        currentPage = 1;
        refreshCustomers();
    }

    public void refreshCustomers() {
        if (search != null && !search.isEmpty()) {
            customers = customerClient.searchCustomers(search, NUM_ENTRIES_ON_PAGE, (currentPage - 1) * NUM_ENTRIES_ON_PAGE);
        } else {
            customers = customerClient.getAllCustomers(NUM_ENTRIES_ON_PAGE, (currentPage - 1) * NUM_ENTRIES_ON_PAGE);
        }
        addresses = customers.stream().collect(Collectors.toMap(
                customer -> customer,
                customer -> customerClient.getAddressFromCustomer(customer)
        ));
    }

    public void nextPage() {
        currentPage++;
    }

    public void previousPage() {
        currentPage--;
    }

    public Address getAddress(Customer customer) {
        return addresses.get(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
