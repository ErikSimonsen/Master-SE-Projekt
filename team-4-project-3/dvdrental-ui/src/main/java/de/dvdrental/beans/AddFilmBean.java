package de.dvdrental.beans;

import de.dvdrental.entities.Actor;
import de.dvdrental.entities.Film;
import de.dvdrental.entities.Store;
import de.dvdrental.microservices.FilmClient;
import de.dvdrental.microservices.StoreClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Named
@ViewScoped
public class AddFilmBean implements Serializable {
    @Inject
    StoreClient storeClient;
    @Inject
    FilmClient filmClient;

    private Film film;
    private Map<Store, Integer> storeToNumInventories;
    private List<Actor> actors;

    public AddFilmBean() {
    }

    @PostConstruct
    public void init() {
        film = new Film();
        storeToNumInventories = storeClient.getAllStores().stream().collect(Collectors.toMap(store -> store, store -> 0));
    }


    public String saveFilm() {
        Integer filmId = filmClient.createFilm(film);
        actors.forEach(actor -> filmClient.addActorToFilm(filmId, actor.getId()));
        storeToNumInventories.forEach((store, numInventories) -> IntStream.range(0, numInventories).forEach(i -> storeClient.createInventory(filmId, store.getId())));

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

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
