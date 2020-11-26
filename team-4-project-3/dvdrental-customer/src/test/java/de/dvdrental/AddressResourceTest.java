package de.dvdrental;

import de.dvdrental.resources.proxies.AddressProxy;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@QuarkusTest
public class AddressResourceTest {

    @Test
    void testGetAddress() {
        given()
                .pathParam("id", 6)
                .when().get("/addresses/{id}")
                .then()
                .header("link", "<http://localhost:8083/addresses/6>; rel=\"self\"; type=\"GET\"")
                .statusCode(200)
                .body("address", is("1121 Loja Avenue"),
                        "address2", is(""),
                        "city", is("San Bernardino"),
                        "country", is("United States"),
                        "district", is("California"),
                        "id", is(6),
                        "phone", is("838635286649"),
                        "postalCode", is( "17886"));
    }

    @Test
    void testGetAddressNotFound() {
        given()
                .pathParam("id", 1000)
                .when().get("/addresses/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testCreateAddress() {
        AddressProxy proxy = new AddressProxy("Braunschweiger Str.", "Unterstraße", "Mannheim", "Germany", "Nordstadt", 8, "12345", "54321");
        String location = given()
                .body(proxy)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/addresses")
                .then()
                .statusCode(201)
                .extract().header("location");

        assertTrue(location.contains("http://localhost:8083/addresses/"));

        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);

        given()
                .pathParam("id", id)
                .when().get("/addresses/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/addresses/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("address", is("Braunschweiger Str."),
                        "address2", is("Unterstraße"),
                        "city", is("Mannheim"),
                        "country", is("Germany"),
                        "district", is("Nordstadt"),
                        "id", is(id),
                        "phone", is("12345"),
                        "postalCode", is( "54321"));
    }

    @Test
    void testCreateAddress2() {
        AddressProxy proxy = new AddressProxy("Lange Straße 151", "Mannheim", "Germany", "Sued", "543210");
        String location = given()
                .body(proxy)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/addresses")
                .then()
                .statusCode(201)
                .extract().header("location");

        assertTrue(location.contains("http://localhost:8083/addresses/"));

        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);

        given()
                .pathParam("id", id)
                .when().get("/addresses/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/addresses/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("address", is("Lange Straße 151"),
                        "city", is("Mannheim"),
                        "country", is("Germany"),
                        "district", is("Sued"),
                        "id", is(id),
                        "phone", is("543210"));
    }

    @Test
    void testCreateAddressNotNullViolations() {
        AddressProxy proxy = new AddressProxy("Lange Straße 151", "Peine", "Germany", null, "543210");
        given()
                .body(proxy)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/addresses")
                .then()
                .statusCode(400);
    }

    @Test
    void testCreateAddressCountryNotFound() {
        AddressProxy proxy = new AddressProxy("Braunschweiger Str.", "Unterstraße", "Mannheim", "NotExisiting", "Nordstadt", 8, "12345", "54321");
        given()
                .body(proxy)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/addresses")
                .then()
                .statusCode(404);
    }

    @Test
    void testCreateAddressCityNotFound() {
        AddressProxy proxy = new AddressProxy("Braunschweiger Str.", "Unterstraße", "Peine", "Germany", "Nordstadt", 8, "12345", "54321");
        given()
                .body(proxy)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when().post("/addresses")
                .then()
                .statusCode(404);
    }
}
