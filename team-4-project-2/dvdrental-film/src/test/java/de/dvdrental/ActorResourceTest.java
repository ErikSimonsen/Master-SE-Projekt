package de.dvdrental;

import de.dvdrental.entities.Actor;
import de.dvdrental.repositories.ActorRepository;
import de.dvdrental.resources.proxies.ActorPostProxy;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ActorResourceTest {
    private static final String FilmsURI = "http://localhost:8080/actors";
    @Inject
    ActorRepository actorRepository;
    @Test
    void testGetAll() {
        get("/actors")
                .then().statusCode(200)
                .body("[0].films.href", is( "http://localhost:8083/actors/1/films"))
                .body("[0].firstName", is("Penelope"))
                .body("[0].id", is(1))
                .body("[0].lastName", is("Guiness"))
                .body("size()", is(100));
    }

    @Test
    void testCreate() {
        ActorPostProxy actorProxy = new ActorPostProxy("Max", 0, "Mustermann");
        String location = given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(actorProxy)
                .when()
                .post("/actors")
                .then()
                .statusCode(201)
                .extract().header("location");
        //parse location and get the id and the path that points to the Actor-Resource
        String[] segments = location.split("/");
        int createdActorId = Integer.parseInt(segments[segments.length - 1]);

        assertTrue(location.startsWith("http://localhost:8083/actors/"));

        given()
                .pathParam("id", createdActorId)
                .when().get("/actors/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/actors/%s>; rel=\"self\"; type=\"GET\"", createdActorId))
                .statusCode(200)
                .body("firstName", is("Max"),
                        "lastName", is("Mustermann"),
                        "id", is(createdActorId),
                        "films.href", is(String.format("http://localhost:8083/actors/%s/films", createdActorId)));
    }

    @Test
    void testCount() {
        Long count = actorRepository.count();
        get("/actors/count").then().statusCode(200).body(is(count.toString()));
    }

    @Test
    void testGet() {
        given()
                .pathParam("id", 4)
                .when().get("/actors/{id}")
                .then()
                .header("link", "<http://localhost:8083/actors/4>; rel=\"self\"; type=\"GET\"")
                .statusCode(200)
                .body("firstName", is("Jennifer"),
                        "lastName", is("Davis"),
                        "id", is(4),
                        "films.href", is("http://localhost:8083/actors/4/films"));
    }

    @Test
    void testGet404() {
        given()
                .pathParam("id", 4000000)
                .when().get("/actors/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testUpdate() {
        ActorPostProxy actorPostProxy = new ActorPostProxy("Moritz", 0, "Mustermann");
        String location = given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(actorPostProxy)
                .when()
                .post("/actors")
                .then()
                .statusCode(201)
                .extract().header("location");

        String[] segments = location.split("/");
        int id = Integer.parseInt(segments[segments.length - 1]);

        given()
                .pathParam("id", id)
                .when().get("/actors/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/actors/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("firstName", is("Moritz"),
                        "lastName", is("Mustermann"),
                        "id", is(id),
                        "films.href", is(String.format("http://localhost:8083/actors/%s/films", id)));

        ActorPostProxy actorPutProxy = new ActorPostProxy("Angela", 10, "Schmidt");
        given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(actorPutProxy)
                .when()
                .pathParam("id", id)
                .put("/actors/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", id)
                .when().get("/actors/{id}")
                .then()
                .header("link", String.format("<http://localhost:8083/actors/%s>; rel=\"self\"; type=\"GET\"", id))
                .statusCode(200)
                .body("firstName", is("Angela"),
                        "lastName", is("Schmidt"),
                        "id", is(id),
                        "films.href", is(String.format("http://localhost:8083/actors/%s/films", id)));
        actorRepository.delete(actorRepository.get(id));

    }

    @Test
    void testDelete() {
        Actor actor = new Actor("Max", "Mustermann");
        actorRepository.create(actor);
        given()
                .when()
                .delete("/actors/" + actor.getActorId())
                .then()
                .statusCode(200);

        Actor actor2 = actorRepository.get(actor.getActorId());
        assertNull(actor2);

        given().when().get("/actors/" + actor.getActorId()).then().statusCode(404);
    }

    @Test
    void testActorFilms() {
        given()
                .when()
                .get("/actors/3/films").then()
                .statusCode(200)
                .body("size()", is(22),
                "[0].title", is("Alone Trip"),
                        "[0].href", is("http://localhost:8083/films/17"),
                        "[4].title", is("Caddyshack Jedi"),
                        "[4].href", is("http://localhost:8083/films/111"));

        given()
                .when()
                .get("/actors/300000/films").then()
                .statusCode(404);
    }
}
