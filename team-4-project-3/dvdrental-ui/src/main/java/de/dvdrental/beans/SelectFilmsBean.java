package de.dvdrental.beans;

import de.dvdrental.entities.*;
import de.dvdrental.microservices.CustomerClient;
import de.dvdrental.microservices.FilmClient;
import de.dvdrental.microservices.StoreClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Named
@ViewScoped
public class SelectFilmsBean implements Serializable {

    private final Integer NUM_ENTRIES_ON_PAGE = 50;

    @Inject
    private FilmClient filmClient;
    @Inject
    private CustomerClient customerClient;
    @Inject
    private StoreClient storeClient;
    @Inject
    private SessionBean sessionBean;

    private Integer customerId;
    private Customer customer;

    private List<Film> films;
    private Map<Film, Boolean> filmToSelected;
    private Map<Film, InventoryStatus> filmToStatus;
    private Set<Film> selectedFilms;
    private List<String> pages;
    private Integer currentPage = 1;

    public SelectFilmsBean() {
    }

    public void loadCustomer() {
        customer = customerClient.getCustomer(customerId);
    }

    @PostConstruct
    public void init() {
        filmToSelected = new HashMap<>();
        filmToStatus = new HashMap<>();
        selectedFilms = new HashSet<>();

        int numPages = (int) Math.ceil(filmClient.getNumFilms().doubleValue() / NUM_ENTRIES_ON_PAGE);
        pages = IntStream.rangeClosed(1, numPages).mapToObj(String::valueOf).collect(Collectors.toList());
        refreshFilms();
    }

    public void refreshFilms() {
        films = filmClient.getAllFilms(NUM_ENTRIES_ON_PAGE, (currentPage - 1) * NUM_ENTRIES_ON_PAGE);
        films.forEach(film -> {
            if (!filmToSelected.containsKey(film)) {
                filmToSelected.put(film, false);
                InventoryStatus status = storeClient.getInventoryStatus(film.getId(), sessionBean.getActiveStaff().getStoreId());
                filmToStatus.put(film, status);
            }
        });

//        filmToSelected = films.stream().collect(Collectors.toMap(film -> film, film -> false));
//        filmToStatus = films.stream().collect(Collectors.toMap(
//                film -> film,
//                film -> ));
    }

    public boolean isNoFilmSelected() {
        return selectedFilms.isEmpty();
    }

    public String getStatusText(Film film) {
        InventoryStatus status = filmToStatus.get(film);
        return status.getNumberOfRents() + "/" + status.getStockNumber();
    }

    public Boolean isFilmAvailable(Film film) {
        return filmToStatus.get(film).getAvailable();
    }

    public String rentSelectedFilms() {
        List<Integer> rentalIds = selectedFilms.stream().map(film -> {
            Inventory freeInventory = filmToStatus.get(film).getFreeInventories().get(0);
            return storeClient.createRental(customer.getId(), new Date(System.currentTimeMillis()), freeInventory.getId(), sessionBean.getActiveStaff().getId());
        }).collect(Collectors.toList());


        String joinedRentalsIds = rentalIds.stream().map(String::valueOf).collect(Collectors.joining(","));

        return String.format("payRentals?faces-redirect=true&customerId=%s&rentalIds=%s",
                              customer.getId(), joinedRentalsIds);
    }

    public void selectFilm(Film film) {
        boolean isSelected = filmToSelected.get(film);
        if (isSelected) {
            selectedFilms.add(film);
        } else {
            selectedFilms.remove(film);
        }
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public Map<Film, Boolean> getFilmToSelected() {
        return filmToSelected;
    }

    public void setFilmToSelected(Map<Film, Boolean> filmToSelected) {
        this.filmToSelected = filmToSelected;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
