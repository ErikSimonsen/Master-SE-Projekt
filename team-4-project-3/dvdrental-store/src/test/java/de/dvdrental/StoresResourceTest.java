package de.dvdrental;

import de.dvdrental.repositories.StoreRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class StoresResourceTest {
    @Inject
    StoreRepository storeRepository;

    @Test
    void testCount() {
        String n = Long.toString(storeRepository.count());
        given()
                .when().get("/stores/count")
                .then()
                .statusCode(200)
                .body(is(n));
    }

    @Test
    void getStore() {
        given()
                .pathParam("id", 1)
                .when().get("/stores/{id}")
                .then()
                .header("link", "<http://localhost:8083/stores/1>; rel=\"self\"; type=\"GET\"")
                .statusCode(200)
                .body("id", is(1));
    }

    @Test
    void getStore404() {
        given()
                .pathParam("id", 19)
                .when().get("/stores/{id}")
                .then()
                .statusCode(404);
    }
}
