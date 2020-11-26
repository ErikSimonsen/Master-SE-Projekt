package de.dvdrental.repositories;

import de.dvdrental.entities.Film;
import de.dvdrental.entities.Inventory;
import de.dvdrental.entities.Store;
import de.dvdrental.repositories.interfaces.RepositoryTest;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(Arquillian.class)
public class InventoryRepositoryTest extends RepositoryTest {

    @Inject
    InventoryRepository inventoryRepository;
    @Inject
    FilmRepository filmRepository;
    @Inject
    StoreRepository storeRepository;

    @Override
    @Test
    public void createAndDelete() {
        Film film = filmRepository.get(67);
        Store store = storeRepository.get(1);
        Inventory in = new Inventory(film, store);
        inventoryRepository.create(in);
        Inventory in2 = inventoryRepository.get(in.getInventoryId());
        Assert.assertEquals(in, in2);
        Assert.assertEquals(in2.getFilm(), film);
        Assert.assertEquals(in2.getStore(), store);

        inventoryRepository.delete(in);
        Assert.assertFalse(inventoryRepository.existsById(in2.getInventoryId()));
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        Inventory in1 = new Inventory(filmRepository.get(10), storeRepository.get(1));
        Inventory in2 = new Inventory(filmRepository.get(125), storeRepository.get(2));
        List<Inventory> ins = Arrays.asList(in1, in2);
        inventoryRepository.createAll(ins);
        Assert.assertEquals(in1, inventoryRepository.get(in1.getInventoryId()));
        Assert.assertEquals(in2, inventoryRepository.get(in2.getInventoryId()));
        inventoryRepository.deleteAll(ins);
        Assert.assertFalse(inventoryRepository.existsById(in1.getInventoryId()));
        Assert.assertFalse(inventoryRepository.existsById(in2.getInventoryId()));
    }

    @Override
    @Test
    public void get() {
        Inventory in = inventoryRepository.get(45);
        Assert.assertEquals(filmRepository.get(9), in.getFilm());
        Assert.assertEquals(storeRepository.get(2), in.getStore());
    }

    @Override
    @Test
    public void getAll() {
        Assert.assertEquals(4581, inventoryRepository.getAll().size());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> ids = IntStream.range(1, 201).boxed().collect(Collectors.toList());
        List<Inventory> inventories = inventoryRepository.getAllById(ids);

        Assert.assertEquals(inventories.get(0).getStore().getStoreId(), 1);
        Assert.assertEquals(inventories.get(0).getFilm().getFilmId(), 1);
        Assert.assertEquals(inventories.get(9).getStore().getStoreId(), 2);
        Assert.assertEquals(inventories.get(9).getFilm().getFilmId(), 2);
        Assert.assertEquals(inventories.get(199).getStore().getStoreId(), 2);
        Assert.assertEquals(inventories.get(199).getFilm().getFilmId(), 44);
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf(4581), inventoryRepository.count());
    }

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(filmRepository.existsById(200));
        Assert.assertFalse(filmRepository.existsById(18000));
    }

    @Override
    @Test
    public void update() {
        Inventory in = inventoryRepository.get(20);
        Assert.assertEquals(filmRepository.get(4), in.getFilm());
        Assert.assertEquals(storeRepository.get(2), in.getStore());

        in.setFilm(filmRepository.get(7));
        in.setStore(storeRepository.get(1));
        Assert.assertEquals(filmRepository.get(7), in.getFilm());
        Assert.assertEquals(storeRepository.get(1), in.getStore());
    }

    @Test
    public void testGetStockNumber() {
        Film film = filmRepository.get(1);
        Assert.assertEquals(4, inventoryRepository.getStockNumber(film, storeRepository.get(1)));
        Assert.assertEquals(4, inventoryRepository.getStockNumber(film, storeRepository.get(2)));


        Film film2 = filmRepository.get(11);
        Assert.assertEquals(4, inventoryRepository.getStockNumber(film2, storeRepository.get(1)));
        Assert.assertEquals(3, inventoryRepository.getStockNumber(film2, storeRepository.get(2)));
        Assert.assertEquals(0, inventoryRepository.getStockNumber(film2, storeRepository.get(3)));
    }

    @Test
    public void testGetAvailableNumber() {
        long num = inventoryRepository.getNumberOfRents(filmRepository.get(189), storeRepository.get(1));
        Assert.assertEquals(2, num);
    }

    @Test
    public void getFreeInventory1() {
        List<Inventory> result = inventoryRepository.getFreeInventory(filmRepository.get(4), storeRepository.get(2));
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(inventoryRepository.get(20), result.get(0));
        Assert.assertEquals(inventoryRepository.get(22), result.get(1));
    }

    @Test
    public void getFreeInventory2() {
        List<Inventory> result = inventoryRepository.getFreeInventory(filmRepository.get(4), storeRepository.get(1));
        Assert.assertEquals(4, result.size());
        Assert.assertEquals(inventoryRepository.get(16), result.get(0));
        Assert.assertEquals(inventoryRepository.get(17), result.get(1));
        Assert.assertEquals(inventoryRepository.get(18), result.get(2));
        Assert.assertEquals(inventoryRepository.get(19), result.get(3));
    }

    @Test
    public void getFreeInventory3() {
        List<Inventory> result = inventoryRepository.getFreeInventory(filmRepository.get(67), storeRepository.get(2));
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(inventoryRepository.get(296), result.get(0));
        Assert.assertEquals(inventoryRepository.get(297), result.get(1));
        Assert.assertEquals(inventoryRepository.get(299), result.get(2));
    }
}
