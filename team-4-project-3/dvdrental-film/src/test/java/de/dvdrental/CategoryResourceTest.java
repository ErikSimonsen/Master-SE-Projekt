package de.dvdrental;

import de.dvdrental.repositories.CategoryRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class CategoryResourceTest {
    @Inject
    CategoryRepository categoryRepository;

    @Test
    void testGetAll() {
        Long categoryCount = categoryRepository.count();
        get("/categories")
                .then()
                .statusCode(200)
                .body("size()", is(categoryCount.intValue()),
                        "[0]", is("Action"),
                        "[1]", is("Animation"));
    }
}
