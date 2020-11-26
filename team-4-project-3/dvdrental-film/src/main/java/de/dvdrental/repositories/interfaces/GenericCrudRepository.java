package de.dvdrental.repositories.interfaces;

import java.util.List;

/**
 * Interface to abstract the typical crud methods trough Generic Types (because the class type and so on
 * is different for each table/entity!), which are normally always implemented in the same way
 * and only the tablename, the entity class type, and the type of the primary key change
 * Inspired by the Spring-Data CRUD Interface.
 * Methods are implemented in the abstract class CrudRepository.java
 *
 * @param <EntityType> Type of the Entity (e.g Actor.class). Needed to specify what class is returned or is contained in the Iterable
 */
public interface GenericCrudRepository<EntityType> {
    void create(EntityType entity);

    void createAll(Iterable<EntityType> entities);

    EntityType get(Integer id);

    List<EntityType> getAll();

    List<EntityType> getAllById(Iterable<Integer> ids);

    boolean existsById(Integer id);

    Long count();

    void update(EntityType entity);

    void delete(EntityType entity);

    void deleteById(Integer id);

    void deleteAll(Iterable<EntityType> entities);
}

