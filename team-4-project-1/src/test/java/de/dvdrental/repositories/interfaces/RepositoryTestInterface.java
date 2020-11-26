package de.dvdrental.repositories.interfaces;

import org.junit.Test;

/**
 * this interface exists to ensure that all test classes for the repositories, have to implement the needed tests.
 */
public interface RepositoryTestInterface {
    /**
     * Create and Delete cant be tested separately, because if both tests are split up and you want to delete data, you have
     * to select it by the primary key (id). But because this is an autoincrement field, you cant use a static value like
     * e.g. customerRepository.get(602). The real underlying problem is that we cant use rollbacks on the tests, so we have to
     * manually "undo" the modified data. If you run the tests multiple times the value you get by customRepository.get(602) always changes
     * or doesnt exist anymore, so you have to get the id of a created entity by using its id field.
     */
    @Test
    void createAndDelete();

    @Test
    void createAllAndDeleteAll();

    @Test
    void get();

    @Test
    void getAll();

    @Test
    void getAllById();

    @Test
    void count();

    @Test
    void existsById();

    @Test
    void update();
}
