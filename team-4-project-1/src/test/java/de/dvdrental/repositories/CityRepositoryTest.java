package de.dvdrental.repositories;

import de.dvdrental.entities.City;
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
public class CityRepositoryTest extends RepositoryTest {

    @Inject
    CityRepository cityRepository;
    @Inject
    CountryRepository countryRepository;

    @Override
    @Test
    public void createAndDelete() {
        City city = new City("Test123", countryRepository.get(1));
        cityRepository.create(city);
        City city2 = cityRepository.get(city.getCityId());
        Assert.assertEquals("Test123", city2.getCity());
        Assert.assertEquals(countryRepository.get(1), city2.getCountry());

        cityRepository.delete(city);
        Assert.assertFalse(cityRepository.existsById(city.getCityId()));
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        City c1 = new City("Test123", countryRepository.get(1));
        City c2 = new City("Test124", countryRepository.get(2));
        List<City> cs = Arrays.asList(c1, c2);
        cityRepository.createAll(cs);
        Assert.assertTrue(cityRepository.existsById(c1.getCityId()));
        Assert.assertTrue(cityRepository.existsById(c2.getCityId()));
        cityRepository.deleteAll(cs);
        Assert.assertFalse(cityRepository.existsById(c1.getCityId()));
        Assert.assertFalse(cityRepository.existsById(c2.getCityId()));
    }

    @Override
    @Test
    public void get() {
        City city = cityRepository.get(2);
        Assert.assertEquals("Abha", city.getCity());
    }

    @Override
    @Test
    public void getAll() {
        List<City> cs = cityRepository.getAll();
        Assert.assertEquals(600, cs.size());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> ids = IntStream.range(1, 201).boxed().collect(Collectors.toList());
        List<City> cs = cityRepository.getAllById(ids);
        Assert.assertEquals(200, cs.size());
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf("600"), cityRepository.count());
    }

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(cityRepository.existsById(1));
        Assert.assertFalse(cityRepository.existsById(601));
    }

    @Override
    @Test
    public void update() {
        City c = cityRepository.get(10);
        Assert.assertEquals("Akishima", c.getCity());
        c.setCity("Test321");
        cityRepository.update(c);
        Assert.assertEquals("Test321", c.getCity());
    }
}
