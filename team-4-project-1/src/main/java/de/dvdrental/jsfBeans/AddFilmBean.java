package de.dvdrental.jsfBeans;

import de.dvdrental.entities.Film;
import de.dvdrental.entities.Inventory;
import de.dvdrental.entities.Store;
import de.dvdrental.repositories.FilmRepository;
import de.dvdrental.repositories.InventoryRepository;
import de.dvdrental.repositories.StoreRepository;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Named
@ViewScoped
public class AddFilmBean implements Serializable {
    @Inject
    private FilmRepository filmRepository;
    @Inject
    private StoreRepository storeRepository;
    @Inject
    private InventoryRepository inventoryRepository;

    private Film film;
    private Map<Store, Integer> storeToNumInventories;

    public AddFilmBean() {
    }

    @PostConstruct
    public void init() {
        film = new Film();
        storeToNumInventories = storeRepository.getAll().stream().collect(Collectors.toMap(store -> store, store -> 0));
    }

    public Object[] getRatings() {
        return Stream.of(Film.Rating.values()).map(rating -> new SelectItem(rating, rating.toString())).toArray();
    }

    public String saveFilm() {
        filmRepository.create(film);
        filmRepository.update(film);

        storeToNumInventories.forEach((store, numInventories) -> IntStream.range(0, numInventories).forEach(i -> {
            Inventory inventory = new Inventory(film, store);
            inventoryRepository.create(inventory);
        }));
        return "films?faces-redirect=true";
    }

    //Getter and setter
    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Map<Store, Integer> getStoreToNumInventories() {
        return storeToNumInventories;
    }

    public void setStoreToNumInventories(Map<Store, Integer> storeToNumInventories) {
        this.storeToNumInventories = storeToNumInventories;
    }
}
