package de.dvdrental;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class StaffResourceTest {
    @Test
    void getStaff() {
        given()
                .pathParam("id", 1)
                .when().get("/staff/{id}")
                .then()
                .header("link", "<http://localhost:8083/staff/1>; rel=\"self\"; type=\"GET\"")
                .statusCode(200)
                .body("active", is(true),
                        "email", is("Mike.Hillyer@sakilastaff.com"),
                        "firstName", is("Mike"),
                        "id", is(1),
                        "lastName", is("Hillyer"),
                        "password", is("8cb2237d0679ca88db6464eac60da96345513964"),
                        "picture", is("89504e470d0a5a0a"),
                        "username", is("Mike"));
    }

    @Test
    void getStaff404() {
        given()
                .pathParam("id", 19)
                .when().get("/staff/{id}")
                .then()
                .statusCode(404);
    }
}
