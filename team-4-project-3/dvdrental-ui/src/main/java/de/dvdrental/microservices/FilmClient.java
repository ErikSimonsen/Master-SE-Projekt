package de.dvdrental.microservices;

import de.dvdrental.entities.Actor;
import de.dvdrental.entities.Film;
import de.dvdrental.entities.Rental;
import de.dvdrental.microservices.interfaces.MicroserviceClient;
import de.dvdrental.microservices.proxies.FilmActorsProxy;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class FilmClient extends MicroserviceClient {

    public FilmClient() {
        super(System.getenv("FILM_MICROSERVICE_ADDRESS"));//super("http://film_microservice:8085");
    }

    public List<Film> searchFilms(String search, Integer limit, Integer offset) {
        return get(
                targetURI + "/films/search/" + search,
                new GenericType<>() {
                },
                Map.of("limit", limit, "offset", offset));
    }
    public Film getFilm(Rental r) {
        return get(r.getFilm().getHref(), new GenericType<>() {});
    }

    public Film getFilm(Integer id) {
        return get(targetURI + "/films/" + id, new GenericType<>() {});
    }

    public List<Film> getAllFilms(Integer limit, Integer offset) {
        return get(targetURI + "/films", new GenericType<>() {}, Map.of("limit", limit, "offset", offset));
    }

    public Integer getNumFilms() {
        return get(targetURI + "/films/count", new GenericType<>() {});
    }

    public Integer createFilm(Film film) {
        return post(targetURI + "/films", film);
    }

    public List<String> getAllCategories() {
        return get(targetURI + "/categories", new GenericType<>() {});
    }

    public List<Actor> getAllActors() {
        return get(targetURI + "/actors", new GenericType<>() {});
    }

    public List<Actor> getActorsOfFilm(Integer filmId) {
        List<FilmActorsProxy> actors = get(String.format("%s/films/%s/actors", targetURI, filmId), new GenericType<>() {});
        return actors.stream().map(actor -> get(actor.getHref(), new GenericType<Actor>() {})).collect(Collectors.toList());
    }

    public Actor getActor(Integer id) {
        return get(targetURI + "/actors/" + id, new GenericType<>() {});
    }

    public void addActorToFilm(Integer filmId, Integer actorId) {
        put(String.format("%s/films/%s/actors/%s", targetURI, filmId, actorId));
    }

    public void addCategoryToFilm(Integer filmId, String category) {
        put(String.format("%s/films/%s/categories/%s", targetURI, filmId, category));
    }

    public List<String> getAllLanguages() {
        return get(targetURI + "/languages", new GenericType<>() {});
//        return client
//                .target(targetURI + "/languages")
//                .request(APPLICATION_JSON)
//                .get(new GenericType<>() {});
    }

    public List<String> getAllRatings() {
        return get(targetURI + "/ratings", new GenericType<>() {});
    }
}
