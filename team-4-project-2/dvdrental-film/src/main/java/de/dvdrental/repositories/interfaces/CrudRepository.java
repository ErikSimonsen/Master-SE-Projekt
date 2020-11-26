package de.dvdrental.repositories.interfaces;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the GenericCrudRepository. Which type the Type Parameters have are determined in the class that extends this
 * class like e.g public class ActorRepository extends CrudRepository<Actor.class, Integer>
 *
 * @param <EntityType> see Interface JavaDoc, only needed so that the Interface can be implemented
 */
public abstract class CrudRepository<EntityType> implements GenericCrudRepository<EntityType> {

    /**
     * Needed because you cant use Type Parameters as normal expressions (so em.find(EntityType, ID) would not work,
     * or a select query with the table name as Type Parameter would not work also))
     * In the extending classes the constructor should be called like e.g. : this.super(Actor.class, "actor")
     */
    protected final Class<EntityType> entityClass;
    protected final String entityClassName;
    @PersistenceContext
    protected EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * @param entity class-type e.g. Actor.class
     */
    public CrudRepository(Class<EntityType> entity) {
        entityClass = entity;
        entityClassName = entity.getName();
    }

    @Transactional
    public void create(EntityType entity) {
        em.persist(entity);
    }

    public void createAll(Iterable<EntityType> entities) {
        for (EntityType entity : entities) {
            create(entity);
        }
    }

    public EntityType get(Integer id) {
        return em.find(entityClass, id);
    }

    public List<EntityType> getAll() {
        return em.createQuery("Select c from " + entityClassName + " c", entityClass).getResultList();
    }

    public List<EntityType> getAllById(Iterable<Integer> ids) {
        ArrayList<EntityType> entities = new ArrayList<>();
        for (Integer id : ids) {
            entities.add(get(id));
        }
        return entities;
    }

    public Long count() {
        return em.createQuery("Select Count(c) from " + entityClassName + " c", Long.class).getSingleResult();
    }

    public boolean existsById(Integer id) {
        return em.find(entityClass, id) != null;
    }

    @Transactional
    public void update(EntityType entity) {
        em.merge(entity);
    }

    @Transactional
    public void delete(EntityType entity) {
        em.remove(em.merge(entity));
    }

    public void deleteById(Integer id) {
        EntityType entity = this.get(id);
        this.delete(entity);
    }

    public void deleteAll(Iterable<EntityType> entities) {
        for (EntityType entity : entities) {
            this.delete(entity);
        }
    }
}
