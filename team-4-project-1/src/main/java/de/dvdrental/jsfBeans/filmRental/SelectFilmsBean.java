package de.dvdrental.jsfBeans.filmRental;

import de.dvdrental.entities.*;
import de.dvdrental.jsfBeans.FilmBean;
import de.dvdrental.jsfBeans.SessionBean;
import de.dvdrental.repositories.CustomerRepository;
import de.dvdrental.repositories.InventoryRepository;
import de.dvdrental.repositories.RentalRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class SelectFilmsBean extends FilmBean {
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private InventoryRepository inventoryRepository;
    @Inject
    private RentalRepository rentalRepository;
    @Inject
    private SessionBean sessionBean;

    private Integer customerIDUrlParameter;
    private Customer customer;
    private Store store;
    private Map<Integer, Boolean> filmToSelected;

    private Set<Film> selectedFilms;

    public SelectFilmsBean() {
    }

    @PostConstruct
    public void init2() {
        selectedFilms = new HashSet<>();
        filmToSelected = new HashMap<>();
    }

    public void loadCustomer() {
        customer = customerRepository.get(customerIDUrlParameter);
        store = customer.getStore();
    }

    public boolean isNoFilmSelected() {
        return selectedFilms.isEmpty();
    }

    public void onSelectFilm(Film film) {
        boolean isSelected = filmToSelected.get(film.getFilmId());
        if (isSelected) {
            selectedFilms.add(film);
        } else {
            selectedFilms.remove(film);
        }
    }

    public long getStockNumber(Film film) {
        return inventoryRepository.getStockNumber(film, store);
    }

    public long getNumberOfRents(Film film) {
        return inventoryRepository.getNumberOfRents(film, store);
    }

    public boolean isFilmAvailable(Film film) {
        long numStock = getStockNumber(film);
        long numAvailable = getNumberOfRents(film);
        return numAvailable < numStock;
    }

    public Map<Integer, Boolean> addFilmAndGetMap(Integer filmId) {
        if (!filmToSelected.containsKey(filmId)) {
            filmToSelected.put(filmId, false);
        }
        return filmToSelected;
    }

    public String rentSelectedFilms() {
        if (selectedFilms.isEmpty()) {
            return "";
        }
        List<Integer> rentalIds = new ArrayList<>();
        // now we add a rental entry in rental table
        for (Film film: selectedFilms) {
            //TODO: We acutally don't need the whole list, just the first element
            List<Inventory> inventories = inventoryRepository.getFreeInventory(film, store);
            Rental rental = new Rental(inventories.get(0), customer, sessionBean.getActiveStaff());
            rentalRepository.create(rental);
            rentalIds.add(rental.getRentalId());
        }

        String joinedRentalsIds = rentalIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        //TODO: Is passing here parameters good practice?
        return String.format("filmRentalPage3?faces-redirect=true&customerid=%s&rentalids=%s",
                customer.getCustomerId(), joinedRentalsIds);
    }

    //Getter and setter
    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Integer getCustomerIDUrlParameter() {
        return customerIDUrlParameter;
    }

    public void setCustomerIDUrlParameter(Integer customerIDUrlParameter) {
        this.customerIDUrlParameter = customerIDUrlParameter;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Set<Film> getSelectedFilms() {
        return selectedFilms;
    }

    public void setSelectedFilms(Set<Film> selectedFilms) {
        this.selectedFilms = selectedFilms;
    }

    public Map<Integer, Boolean> getFilmToSelected() {
        return filmToSelected;
    }

    public void setFilmToSelected(Map<Integer, Boolean> filmToSelected) {
        this.filmToSelected = filmToSelected;
    }

}
