package de.dvdrental.repositories;

import de.dvdrental.entities.Actor;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class ActorRepository extends CrudRepository<Actor> {
    public ActorRepository() {
        super(Actor.class);
    }
}
