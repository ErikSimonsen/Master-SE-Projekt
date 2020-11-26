package de.dvdrental.repositories;

import de.dvdrental.entities.Actor;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class ActorRepository extends CrudRepository<Actor> {
    public ActorRepository() {
        super(Actor.class);
    }

    public List<Actor> getAll(Integer limit) {
        return em.createQuery("select a from Actor a", Actor.class).setMaxResults(limit).getResultList();
    }
}
