package de.dvdrental;

import de.dvdrental.repositories.RentalRepository;
import de.dvdrental.resources.proxies.RentalPostProxy;
import de.dvdrental.resources.utils.Href;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class RentalsResourceTest {

    @ConfigProperty(name = "customer.service.uri")
    protected Optional<String> customerUri;

    @ConfigProperty(name = "film.service.uri")
    protected Optional<String> filmUri;

    @Inject
    RentalRepository rentalRepository;

    @Test
    void testCreateRental() {
        //RentalPostProxy proxy = new RentalPostProxy(2, 3, 1, LocalDateTime.of(2019, 8, 5, 13, 15));
        String location = given()
                //.body(proxy)
                .body("{\"inventory\":234,\"customer\":215,\"staff\":1,\"date\":\"2020-04-06 15:09\"}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/rentals")
                .then()
                .statusCode(201)
                .extract().header("location");

        assertTrue(location.contains("http://localhost:8083/rentals/"));

        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);

        String customer = customerUri.map(s -> UriBuilder.fromPath(s)
                .path("customers")
                .path("215")
                .build().toString()).orElse("");

        String film = filmUri.map(s -> UriBuilder.fromPath(s)
                .path("films")
                .path("52")
                .build().toString()).orElse("");
        given()
                .pathParam("id", id)
                .when().get("/rentals/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/rentals/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("customer.href", is(customer),
                        "film.href", is(film),
                        "rentalDate", is("2020-04-06T15:09:00"),
                        "rentalId", is(id),
                        "store.href", is("http://localhost:8083/stores/2"));

        rentalRepository.delete(rentalRepository.get(id));
    }

    @Test
    void testCreateRental404() {
        given()
                .body("{\"inventory\":23400000,\"customer\":217,\"staff\":1,\"date\":\"2020-04-06 15:09\"}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/rentals")
                .then()
                .statusCode(404);

        given()
                .body("{\"inventory\":234,\"customer\":217,\"staff\":10,\"date\":\"2020-04-06 15:09\"}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/rentals")
                .then()
                .statusCode(404);
    }

    @Test
    void testCreateRental400() {
        given()
                .body("{\"inventory\":234,\"customer\":217,\"staff\":10}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/rentals")
                .then()
                .statusCode(400);
    }

    @Test
    void testCreateRental409() {
        String location = given()
                .body("{\"inventory\":235,\"customer\":218,\"staff\":1,\"date\":\"2020-04-07 15:09\"}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/rentals")
                .then()
                .statusCode(201)
                .extract().header("location");

        assertTrue(location.contains("http://localhost:8083/rentals/"));

        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);

        given()
                .body("{\"inventory\":235,\"customer\":218,\"staff\":1,\"date\":\"2020-04-07 15:09\"}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/rentals")
                .then()
                .statusCode(409);

        rentalRepository.delete(rentalRepository.get(id));
    }

    @Test
    void testGetRental() {
        String customer = customerUri.map(s -> UriBuilder.fromPath(s)
                .path("customers")
                .path("399")
                .build().toString()).orElse("");

        String film = filmUri.map(s -> UriBuilder.fromPath(s)
                .path("films")
                .path("396")
                .build().toString()).orElse("");

        given()
                .pathParam("id", 10)
                .when().get("/rentals/{id}")
                .then()
                .header("link", "<http://localhost:8083/rentals/10>; rel=\"self\"; type=\"GET\"")
                .statusCode(200)
                .body("customer.href", is(customer),
                        "film.href", is(film),
                        "rentalDate", is("2005-05-25T00:02:21"),
                        "rentalId", is(10),
                        "returnDate", is("2005-05-31T22:44:21"),
                        "store.href", is("http://localhost:8083/stores/2"));
    }

    @Test
    void testGetRental404() {
        given()
                .pathParam("id", 100000)
                .when().get("/rentals/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testReturnRental() {
        String location = given()
                //.body(proxy)
                .body("{\"inventory\":227,\"customer\":212,\"staff\":1,\"date\":\"2020-04-06 15:09\"}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/rentals")
                .then()
                .statusCode(201)
                .extract().header("location");

        assertTrue(location.contains("http://localhost:8083/rentals/"));

        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);

        given()
                .pathParam("id", id)
                .when().put("/rentals/{id}/returned")
                .then()
                .statusCode(200)
                .body(is(String.format("\"http://localhost:8083/rentals/%s\"", id)));

        String customer = customerUri.map(s -> UriBuilder.fromPath(s)
                .path("customers")
                .path("212")
                .build().toString()).orElse("");

        String film = filmUri.map(s -> UriBuilder.fromPath(s)
                .path("films")
                .path("51")
                .build().toString()).orElse("");

        String returnDate = given()
                .pathParam("id", id)
                .when().get("/rentals/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/rentals/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("customer.href", is(customer),
                        "film.href", is(film),
                        "rentalDate", is("2020-04-06T15:09:00"),
                        "rentalId", is(id),
                        "store.href", is("http://localhost:8083/stores/1"))
                .extract().body().path("returnDate");
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertTrue(returnDate.startsWith(date));

        rentalRepository.delete(rentalRepository.get(id));
    }

    @Test
    void testReturnRental404() {
        given()
                .pathParam("id", 5000000)
                .when().put("/rentals/{id}/returned")
                .then()
                .statusCode(404);
    }
}
