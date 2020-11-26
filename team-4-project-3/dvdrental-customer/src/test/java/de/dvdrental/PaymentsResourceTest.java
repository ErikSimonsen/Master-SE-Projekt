package de.dvdrental;

import de.dvdrental.resources.proxies.PaymentPostProxy;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.UriBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@QuarkusTest
public class PaymentsResourceTest {

    @ConfigProperty(name = "store.service.uri")
    protected Optional<String> storeUri;


    @Test
    void getPayment() {
        String rentalHref = storeUri.map(s -> UriBuilder.fromPath(s)
                .path("rentals")
                .path("3250")
                .build()
                .toString()).orElse("");
        String staffHref = storeUri.map(s -> UriBuilder.fromPath(s)
                .path("staff")
                .path("1")
                .build()
                .toString()).orElse("");

        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .pathParam("id", 18000)
                .when().get("/payments/{id}")
                .then()
                .header("link", "<http://localhost:8083/payments/18000>; rel=\"self\"; type=\"GET\"")
                .statusCode(200)
                .body("amount", is(new BigDecimal("6.99")),
                        "customer.href", is("http://localhost:8083/customers/467"),
                        "id", is(18000),
                        "rental.href", is(rentalHref),
                        "staff.href", is(staffHref));
    }

    @Test
    void getPaymentNotFound() {
        given()
                .pathParam("id", 1)
                .when().get("/payments/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void createPayment() {
        String location = given()
                .body("{\"amount\":3.25,\"rental\":9,\"customer\":4,\"staff\":2,\"date\":\"2020-02-14 11:56\"}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/payments")
                .then()
                .statusCode(201)
                .extract().header("location");

        assertTrue(location.startsWith("http://localhost:8083/payments/"));
        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);

        String rentalHref = storeUri.map(s -> UriBuilder.fromPath(s)
                .path("rentals")
                .path("9")
                .build()
                .toString()).orElse("");
        String staffHref = storeUri.map(s -> UriBuilder.fromPath(s)
                .path("staff")
                .path("2")
                .build()
                .toString()).orElse("");

        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .pathParam("id", id)
                .when().get("/payments/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/payments/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("amount", is(new BigDecimal("3.25")),
                        "customer.href", is("http://localhost:8083/customers/4"),
                        "id", is(id),
                        "rental.href", is(rentalHref),
                        "staff.href", is(staffHref));
    }

    @Test
    void testCreatePaymentCustomerNotFound() {
        given()
                .body("{\"amount\":3.25,\"rental\":9,\"customer\":40000,\"staff\":2,\"date\":\"2020-02-14 11:56\"}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/payments")
                .then()
                .statusCode(404);
    }

    @Test
    void testCreatePaymentNotNullViolation() {
        given()
                .body("{\"rental\":9,\"customer\":40000,\"staff\":2,\"date\":\"2020-02-14 11:56\"}")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/payments")
                .then()
                .statusCode(400);
    }

}
