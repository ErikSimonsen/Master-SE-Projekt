package de.dvdrental.beans;

import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Film;
import de.dvdrental.entities.Rental;
import de.dvdrental.microservices.CustomerClient;
import de.dvdrental.microservices.FilmClient;
import de.dvdrental.microservices.StoreClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Named
@ViewScoped
public class RentalBean implements Serializable {
    @Inject
    StoreClient storeClient;
    @Inject
    CustomerClient customerClient;
    @Inject
    FilmClient filmClient;
    private String search;

    private final Integer NUM_ENTRIES_ON_PAGE = 50;

    private List<Rental> rentals;
    private Map<Rental, Film> rentalFilmMap;
    private Map<Rental, Customer> rentalCustomerMap;
    private List<String> pages;
    private Integer currentPage = 1;

    public RentalBean() {
    }

    @PostConstruct
    public void init() {
        int numPages = (int) Math.ceil(storeClient.countRentals().doubleValue() / NUM_ENTRIES_ON_PAGE);
        pages = IntStream.rangeClosed(1, numPages).mapToObj(String::valueOf).collect(Collectors.toList());
        rentalFilmMap = new HashMap<>();
        rentalCustomerMap = new HashMap<>();
        refreshRentals();
    }

    public void onSearchChange() {
        currentPage = 1;
        refreshRentals();
    }

    public void refreshRentals() {
        if (search != null && !search.isEmpty()) {
            rentals = storeClient.searchRentals(search, NUM_ENTRIES_ON_PAGE, (currentPage - 1) * NUM_ENTRIES_ON_PAGE);
        } else {
            rentals = storeClient.getAllRentals(NUM_ENTRIES_ON_PAGE, (currentPage - 1) * NUM_ENTRIES_ON_PAGE);
        }
        rentalFilmMap.clear();
        rentalCustomerMap.clear();
        for (Rental r : rentals) {
            Customer customer = customerClient.getCustomerOfRental(r);
            Film film = filmClient.getFilm(r);
            rentalFilmMap.put(r, film);
            rentalCustomerMap.put(r, customer);
        }
    }

    public void nextPage() {
        currentPage++;
    }

    public void previousPage() {
        currentPage--;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public Customer getRentalCustomer(Rental rental) {
        return rentalCustomerMap.get(rental);
    }

    public Film getRentalFilm(Rental rental) {
        return rentalFilmMap.get(rental);
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public Map<Rental, Film> getRentalFilmMap() {
        return rentalFilmMap;
    }

    public void setRentalFilmMap(Map<Rental, Film> rentalFilmMap) {
        this.rentalFilmMap = rentalFilmMap;
    }

    public Map<Rental, Customer> getRentalCustomerMap() {
        return rentalCustomerMap;
    }

    public void setRentalCustomerMap(Map<Rental, Customer> rentalCustomerMap) {
        this.rentalCustomerMap = rentalCustomerMap;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
