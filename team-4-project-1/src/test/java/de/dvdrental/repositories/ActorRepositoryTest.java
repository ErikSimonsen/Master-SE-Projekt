package de.dvdrental.repositories;

import de.dvdrental.entities.Actor;
import de.dvdrental.repositories.interfaces.RepositoryTest;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(Arquillian.class)
public class ActorRepositoryTest extends RepositoryTest {

    @Inject
    ActorRepository actorRep;

    @Override
    @Test
    public void createAndDelete() {
        //First creating
        Actor actor = new Actor("Max", "Mustermann");
        actorRep.create(actor);
        Assert.assertEquals(actor, actorRep.get(actor.getActorId()));

        //now deleting
        actorRep.delete(actor);
        Assert.assertNull(actorRep.get(actor.getActorId()));
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        Actor actor1 = new Actor("Max", "Mustermann");
        Actor actor2 = new Actor("Lisa", "Frieda");
        Actor actor3 = new Actor("Martin", "Bosch");

        List<Actor> actors = Arrays.asList(actor1, actor2, actor3);
        actorRep.createAll(actors);

        Assert.assertEquals(actor1, actorRep.get(actor1.getActorId()));
        Assert.assertEquals(actor2, actorRep.get(actor2.getActorId()));
        Assert.assertEquals(actor3, actorRep.get(actor3.getActorId()));

        actorRep.deleteAll(actors);
        Assert.assertFalse(actorRep.existsById(actor1.getActorId()));
        Assert.assertFalse(actorRep.existsById(actor2.getActorId()));
        Assert.assertFalse(actorRep.existsById(actor3.getActorId()));
    }

    @Override
    @Test
    public void get() {
        Actor actor = actorRep.get(2);
        Assert.assertEquals(2, actor.getActorId());
        Assert.assertEquals("Nick", actor.getFirstName());
        Assert.assertEquals("Wahlberg", actor.getLastName());
    }

    @Override
    @Test
    public void getAll() {
        Assert.assertEquals(200, actorRep.getAll().size());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> ids = IntStream.range(1, 21).boxed().collect(Collectors.toList());
        List<Actor> actors = actorRep.getAllById(ids);
        Assert.assertEquals(20, actors.size());

        Actor actor = actors.get(1);
        Assert.assertEquals(2, actor.getActorId());
        Assert.assertEquals("Nick", actor.getFirstName());
        Assert.assertEquals("Wahlberg", actor.getLastName());
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf(200), actorRep.count());
    }

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(actorRep.existsById(34));
        Assert.assertFalse(actorRep.existsById(252));
    }

    @Override
    @Test
    public void update() {
        //First save
        Actor actor = new Actor("Max", "Mustermann");
        actorRep.create(actor);
        Assert.assertEquals(actor, actorRep.get(actor.getActorId()));

        //now updating
        actor.setFirstName("Lisa");
        actorRep.update(actor);
        Assert.assertEquals("Lisa", actor.getFirstName());
        Assert.assertEquals("Lisa", actorRep.get(actor.getActorId()).getFirstName());

        actor.setFirstName("Max");
        actorRep.update(actor);
        Assert.assertEquals("Max", actor.getFirstName());
        Assert.assertEquals("Max", actorRep.get(actor.getActorId()).getFirstName());

        //now deleting
        actorRep.delete(actor);
        Assert.assertNull(actorRep.get(actor.getActorId()));
    }
}
