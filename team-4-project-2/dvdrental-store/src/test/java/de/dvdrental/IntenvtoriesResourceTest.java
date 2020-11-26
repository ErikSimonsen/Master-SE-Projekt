package de.dvdrental;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class IntenvtoriesResourceTest {

    @Test
    void testGetInventory() {
        given()
                .pathParam("id", 6)
                .when().get("/inventories/{id}")
                .then()
                .header("link", "<http://localhost:8083/inventories/6>; rel=\"self\"; type=\"GET\"")
                .statusCode(200)
                .body("id", is(6));
    }

    @Test
    void testGetInventoryNotFound() {
        given()
                .pathParam("id", 60000)
                .when().get("/inventories/{id}")
                .then()
                .statusCode(404);
    }
}
