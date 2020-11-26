package de.dvdrental.repositories;

import de.dvdrental.entities.Category;
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
public class CategoryRepositoryTest extends RepositoryTest {
    @Inject
    CategoryRepository categoryRep;

    @Override
    @Test
    public void createAndDelete() {
        Category cat = new Category("Indie");
        categoryRep.create(cat);
        Assert.assertTrue(categoryRep.existsById(cat.getCategoryId()));
        Assert.assertEquals(cat, categoryRep.get(cat.getCategoryId()));


        categoryRep.delete(cat);
        Assert.assertFalse(categoryRep.existsById(cat.getCategoryId()));
        Assert.assertNull(categoryRep.get(cat.getCategoryId()));
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        Category cat1 = new Category("Test1");
        Category cat2 = new Category("Test2");
        Category cat3 = new Category("Test3");
        List<Category> categories = Arrays.asList(cat1, cat2, cat3);

        categoryRep.createAll(categories);

        Assert.assertEquals(cat1, categoryRep.get(cat1.getCategoryId()));
        Assert.assertEquals(cat2, categoryRep.get(cat2.getCategoryId()));
        Assert.assertEquals(cat3, categoryRep.get(cat3.getCategoryId()));


        categoryRep.deleteAll(categories);
        Assert.assertNull(categoryRep.get(cat1.getCategoryId()));
        Assert.assertNull(categoryRep.get(cat2.getCategoryId()));
        Assert.assertNull(categoryRep.get(cat3.getCategoryId()));

    }

    @Override
    @Test
    public void get() {
        Category cat = categoryRep.get(2);
        Assert.assertEquals("Animation", cat.getName());
    }

    @Override
    @Test
    public void getAll() {
        Assert.assertEquals(16, categoryRep.getAll().size());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> list = IntStream.range(1, 16).boxed().collect(Collectors.toList());
        List<Category> categories = categoryRep.getAllById(list);
        for (Category category : categories) {
            Assert.assertEquals(categoryRep.get(category.getCategoryId()), category);
        }
        Assert.assertEquals(15, categories.size());
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf(16), categoryRep.count());
    }

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(categoryRep.existsById(1));
    }

    @Override
    @Test
    public void update() {
        Category cat = new Category("Indie2");
        categoryRep.create(cat);
        Assert.assertEquals(cat, categoryRep.get(cat.getCategoryId()));

        cat.setName("Indie3");
        categoryRep.update(cat);
        Assert.assertEquals("Indie3", categoryRep.get(cat.getCategoryId()).getName());

        cat.setName("Indie2");
        categoryRep.update(cat);
        Assert.assertEquals("Indie2", categoryRep.get(cat.getCategoryId()).getName());

        categoryRep.delete(cat);
        Assert.assertFalse(categoryRep.existsById(cat.getCategoryId()));
    }
}
