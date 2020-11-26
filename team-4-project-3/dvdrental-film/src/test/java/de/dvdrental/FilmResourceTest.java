package de.dvdrental;

import de.dvdrental.entities.Actor;
import de.dvdrental.entities.Category;
import de.dvdrental.entities.Film;
import de.dvdrental.repositories.ActorRepository;
import de.dvdrental.repositories.CategoryRepository;
import de.dvdrental.repositories.FilmRepository;
import de.dvdrental.repositories.LanguageRepository;
import de.dvdrental.resources.proxies.FilmProxy;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class FilmResourceTest {
    @Inject
    FilmRepository filmRepository;
    @Inject
    LanguageRepository languageRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    ActorRepository actorRepository;

    @Test
    void testGetFilmList() {

        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .when()
                .get("/films?limit=100")
                .then()
                .statusCode(200)
                .body("[0].actors.href", is("http://localhost:8083/films/1/actors"),
                        "[0].categories[0]", is("Documentary"),
                        "[0].categories.size()", is(1),
                        "[0].description", is("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies"),
                        "[0].id", is(1),
                        "[0].language", is("English"),
                        "[0].length", is(86),
                        "[0].rating", is("PG"),
                        "[0].releaseYear", is(2006),
                        "[0].rentalRate", is(new BigDecimal("0.99")),
                        "[0].title", is("Academy Dinosaur"),
                        "[99].id", is(100),
                        "size()", is(100));

        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .when()
                .get("/films")
                .then()
                .statusCode(200)
                .body("[0].actors.href", is("http://localhost:8083/films/1/actors"),
                        "[0].categories[0]", is("Documentary"),
                        "[0].categories.size()", is(1),
                        "[0].description", is("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies"),
                        "[0].id", is(1),
                        "[0].language", is("English"),
                        "[0].length", is(86),
                        "[0].rating", is("PG"),
                        "[0].releaseYear", is(2006),
                        "[0].rentalRate", is(new BigDecimal("0.99")),
                        "[0].title", is("Academy Dinosaur"),
                        "[99].id", is(100),
                        "size()", is(100));

        given().when().get("/films?limit=50&offset=50").then()
                .body("[0].id", is(51))
                .body("[49].id", is(100))
                .body("size()", is(50));
    }

    @Test
    void testCreateFilm() {
        FilmProxy filmProxy = new FilmProxy();

        filmProxy.setTitle("TestTitle");
        filmProxy.setDescription("TestDescription");
        filmProxy.setReleaseYear((short) 2000);
        filmProxy.setRentalRate(new BigDecimal("3.50"));
        filmProxy.setRentalDuration((short) 3);
        filmProxy.setReplacementCost(new BigDecimal("4.30")); //dont initialize BigDecimal with a double value, because some values cant be represented correctly (e.g. 0.3)
        filmProxy.setLength((short) 120);
        filmProxy.setRating("R");
        filmProxy.setLanguage("German");
        filmProxy.getCategories().add("Action");
        filmProxy.getCategories().add("Travel");

        String location = given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(filmProxy)
                .when().post("/films")
                .then()
                .statusCode(201)
                .extract().header("location");

        assertTrue(location.startsWith("http://localhost:8083/films/"));

        //parse location and get the id that points to the Film-Resource
        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);


        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .pathParam("id", id)
                .when().get("/films/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/films/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("actors.href", is(String.format("http://localhost:8083/films/%s/actors", id)),
                        //"categories[0]", is("Action"),
                        //"categories[1]", is("Travel"),
                        "categories", hasItems("Action", "Travel"),
                        "description", is("TestDescription"),
                        "id", is(id),
                        "language", is("German"),
                        "length", is(120),
                        "rating", is("R"),
                        "releaseYear", is(2000),
                        "rentalRate", is(new BigDecimal("3.50")),
                        "title", is("TestTitle"));

        filmRepository.delete(filmRepository.get(id));
    }

    @Test
    void testCreateFilm2() {
        FilmProxy filmProxy = new FilmProxy();

        filmProxy.setTitle("TestTitle");
        filmProxy.setDescription("TestDescription");
        filmProxy.setReleaseYear((short) 2000);
        filmProxy.setRentalRate(new BigDecimal("3.50"));
        filmProxy.setRentalDuration((short) 3);
        filmProxy.setReplacementCost(new BigDecimal("4.30")); //dont initialize BigDecimal with a double value, because some values cant be represented correctly (e.g. 0.3)
        filmProxy.setLength((short) 120);
        filmProxy.setRating("R");
        filmProxy.setLanguage("GibtEsNicht");
        filmProxy.getCategories().add("Action");
        filmProxy.getCategories().add("Travel");

        given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(filmProxy)
                .when().post("/films")
                .then()
                .statusCode(404);
    }

    @Test
    void testFilmCount() {
        Long count = filmRepository.count();
        get("/films/count").then().statusCode(200).body(is(count.toString()));
    }

    @Test
    void testGetFilm() {
        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .pathParam("id", 25)
                .when().get("/films/{id}")
                .then()
                .header("link", "<http://localhost:8083/films/25>; rel=\"self\"; type=\"GET\"")
                .statusCode(200)
                .body("actors.href", is("http://localhost:8083/films/25/actors"),
                        "categories[0]", is("New"),
                        "categories.size()", is(1),
                        "description", is("A Thoughtful Display of a Woman And a Astronaut who must Battle a Robot in Berlin"),
                        "id", is(25),
                        "language", is("English"),
                        "length", is(74),
                        "rating", is("G"),
                        "releaseYear", is(2006),
                        "rentalRate", is(new BigDecimal("2.99")),
                        "title", is("Angels Life"));

        given()
                .pathParam("id", 2500000)
                .when().get("/films/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetFilmActors() {
        given()
                .when().get("/films/5/actors").then()
                .statusCode(200)
                .body("size()", is(5),
                        "firstname-lastname", hasItems("Gary Phoenix", "Dustin Tautou", "Matthew Leigh", "Matthew Carrey", "Thora Temple"),
                        "href", hasItems("http://localhost:8083/actors/51", "http://localhost:8083/actors/59", "http://localhost:8083/actors/103",
                                 "http://localhost:8083/actors/181", "http://localhost:8083/actors/200")
                );

        given()
                .when().get("/films/50000/actors").then()
                .statusCode(404);
    }

    @Test
    void testFilmAddActor() {
        Actor actor = actorRepository.get(11);
        given().when().put("/films/1/actors/11").then().statusCode(200);
        given().when().get("/films/1/actors").then().statusCode(200)
                .body("firstname-lastname", hasItem(actor.getFirstName() + " " + actor.getLastName()));
        Film film = filmRepository.get(1);
        film.getActors().remove(actor);
        filmRepository.update(film);

        given().when().put("/films/1000000/actors/11").then().statusCode(404);
        given().when().put("/films/1/actors/10000").then().statusCode(404);
    }

    @Test
    void testFilmAddCategory() {
        Category category = categoryRepository.getByName("action");
        given().when().put("/films/1/categories/action").then().statusCode(200);
        given().when().get("/films/1").then().statusCode(200).
                body("categories", hasItem(category.getName()));
        Film film = filmRepository.get(1);
        film.getCategories().remove(category);
        filmRepository.update(film);

        given().when().put("/films/1/categories/test").then().statusCode(404);
        given().when().put("/films/1000000/categories/action").then().statusCode(404);
    }
}
