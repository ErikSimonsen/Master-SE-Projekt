package de.dvdrental;

import de.dvdrental.repositories.LanguageRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class LanguageResourceTest {
    @Inject
    LanguageRepository languageRepository;

    @Test
    void testGetAll() {
        Long languagesCount = languageRepository.count();
        get("/languages").then().statusCode(200).body("size()", is(languagesCount.intValue()),
                "$", hasItems("English", "Italian", "French", "German", "Mandarin", "Japanese"));
    }
}
