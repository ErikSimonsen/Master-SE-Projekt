package de.dvdrental;

import de.dvdrental.repositories.CustomerRepository;
import de.dvdrental.resources.proxies.CustomerProxy;
import de.dvdrental.resources.utils.Href;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@QuarkusTest
class CustomerResourceTest {

    @ConfigProperty(name = "store.service.uri")
    protected Optional<String> storeUri;

    @Inject
    CustomerRepository customerRepository;

    @Test
    void testGetCustomer() {
        String storeHref = storeUri.map(s -> UriBuilder.fromPath(s)
                .path("stores")
                .path("1")
                .build()
                .toString()).orElse("");

        given()
                .pathParam("id", 1)
                .when().get("/customers/{id}")
                .then()
                .header("link", "<http://localhost:8083/customers/1>; rel=\"self\"; type=\"GET\"")
                .statusCode(200)
                .body("active", is(1),
                        "activebool", is(true),
                        "address.href", is("http://localhost:8083/addresses/5"),
                        "email", is("mary.smith@sakilacustomer.org"),
                        "firstName", is("Mary"),
                        "id", is(1),
                        "lastName", is("Smith"),
                        "store.href", is(storeHref));
    }

    @Test
    void testGetCustomerNotFound() {
        given()
                .pathParam("id", 1000000)
                .when().get("/customers/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testCustomerCount() {
        String n = Long.toString(customerRepository.count());
        given()
                .when().get("/customers/count")
                .then()
                .statusCode(200)
                .body(is(n));
    }

    @Test
    void testPostCustomerAdressNotFound() {
        CustomerProxy proxy = new CustomerProxy(true, "Max", "Mustermann");
        given()
                .body(proxy)
                .queryParam("address", 100000)
                .queryParam("store", 1)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/customers")
                .then()
                .statusCode(404);
    }

    @Test
    void testPostCustomerNotNullViolation() {
        CustomerProxy proxy = new CustomerProxy(true, "Max", null);
        given()
                .body(proxy)
                .queryParam("address", 100000)
                .queryParam("store", 1)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/customers")
                .then()
                .statusCode(400);
    }

    @Test
    void testPostCustomer() {
        CustomerProxy proxy = new CustomerProxy(true, "Max", "Mustermann");
        String location = given()
                .body(proxy)
                .queryParam("address", 7)
                .queryParam("store", 1)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/customers")
                .then()
                .statusCode(201)
                .extract().header("location");

        assertTrue(location.contains("http://localhost:8083/customers/"));

        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);

        String storeHref = storeUri.map(s -> UriBuilder.fromPath(s)
                .path("stores")
                .path("1")
                .build()
                .toString()).orElse("");

        given()
                .pathParam("id", id)
                .when().get("/customers/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/customers/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("activebool", is(true),
                        "address.href", is("http://localhost:8083/addresses/7"),
                        "firstName", is("Max"),
                        "id", is(id),
                        "lastName", is("Mustermann"),
                        "store.href", is(storeHref));

        customerRepository.delete(customerRepository.get(id));
    }

    @Test
    void testPostCustomer2() {
        CustomerProxy proxy = new CustomerProxy(1, false, Href.fromString("http://localhost:8083/addresses/7"), null, "max.mustermann@gmail.com",
                "Max", 5, "Mustermann", Href.fromString("http://localhost:8083/store/1"));
        String location = given()
                .body(proxy)
                .queryParam("address", 8)
                .queryParam("store", 2)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/customers")
                .then()
                .statusCode(201)
                .extract().header("location");

        assertTrue(location.contains("http://localhost:8083/customers/"));

        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);

        String storeHref = storeUri.map(s -> UriBuilder.fromPath(s)
                .path("stores")
                .path("2")
                .build()
                .toString()).orElse("");

        given()
                .pathParam("id", id)
                .when().get("/customers/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/customers/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("activebool", is(false),
                        "address.href", is("http://localhost:8083/addresses/8"),
                        "firstName", is("Max"),
                        "id", is(id),
                        "lastName", is("Mustermann"),
                        "store.href", is(storeHref),
                        "email", is("max.mustermann@gmail.com"),
                        "active", is(1));

        customerRepository.delete(customerRepository.get(id));
    }

    @Test
    void testGetCustomerPayments() {
        List<String> responses = given()
                .pathParam("id", 2)
                .when().get("/customers/{id}/payments")
                .then()
                .statusCode(200)
                .extract().body().path("href");

        assertEquals(26, responses.size());
        List<String> paths = Arrays.asList("http://localhost:8083/payments/18502",
                "http://localhost:8083/payments/22691",
                "http://localhost:8083/payments/22692",
                "http://localhost:8083/payments/22693",
                "http://localhost:8083/payments/22694",
                "http://localhost:8083/payments/22695",
                "http://localhost:8083/payments/22696",
                "http://localhost:8083/payments/22697",
                "http://localhost:8083/payments/22698",
                "http://localhost:8083/payments/22699",
                "http://localhost:8083/payments/22700",
                "http://localhost:8083/payments/22701",
                "http://localhost:8083/payments/29005",
                "http://localhost:8083/payments/29006",
                "http://localhost:8083/payments/29007",
                "http://localhost:8083/payments/29008",
                "http://localhost:8083/payments/29009",
                "http://localhost:8083/payments/29010",
                "http://localhost:8083/payments/29011",
                "http://localhost:8083/payments/29012",
                "http://localhost:8083/payments/29013",
                "http://localhost:8083/payments/29014",
                "http://localhost:8083/payments/29015",
                "http://localhost:8083/payments/29016",
                "http://localhost:8083/payments/29017",
                "http://localhost:8083/payments/29018");

        assertEquals(paths, responses);
    }

    @Test
    void testGetCustomerPaymentsNotFound() {
        given()
                .pathParam("id", 20000)
                .when().get("/customers/{id}/payments")
                .then()
                .statusCode(404);
    }


}
