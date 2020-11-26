package de.dvdrental.repositories;

import de.dvdrental.entities.Address;
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
public class AddressRepositoryTest extends RepositoryTest {

    @Inject
    AddressRepository addressRepository;
    @Inject
    CityRepository cityRepository;

    @Override
    @Test
    public void createAndDelete() {
        Address a = new Address("Salzdahlumer Str.", "Genauere Adresse", "Exer", "31254", "1547515", cityRepository.get(1));
        addressRepository.create(a);
        Address a2 = addressRepository.get(a.getAddressId());
        Assert.assertEquals("Salzdahlumer Str.", a.getAddress());
        Assert.assertEquals("Genauere Adresse", a.getAddress2());
        Assert.assertEquals("Exer", a.getDistrict());
        Assert.assertEquals("31254", a.getPostalCode());
        Assert.assertEquals("1547515", a.getPhone());
        Assert.assertEquals(cityRepository.get(1), a.getCity());

        addressRepository.delete(a);
        Assert.assertFalse(addressRepository.existsById(a.getAddressId()));
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        Address a1 = new Address("Salzdahlumer Str.", "Genauere Adresse", "Exer", "31254", "1547515", cityRepository.get(1));
        Address a2 = new Address("Random Str.", "Genauere Adresse", "Exe", "31255", "1547516", cityRepository.get(2));
        List<Address> as = Arrays.asList(a1, a2);
        addressRepository.createAll(as);
        Assert.assertEquals("Salzdahlumer Str.", a1.getAddress());
        Assert.assertEquals("Random Str.", a2.getAddress());
        Assert.assertEquals(a1, addressRepository.get(a1.getAddressId()));
        Assert.assertEquals(a2, addressRepository.get(a2.getAddressId()));

        addressRepository.deleteAll(as);
        Assert.assertFalse(addressRepository.existsById(a1.getAddressId()));
        Assert.assertFalse(addressRepository.existsById(a2.getAddressId()));
    }

    @Override
    @Test
    public void get() {
        Address a = addressRepository.get(5);
        Assert.assertEquals("1913 Hanoi Way", a.getAddress());
        Assert.assertEquals("", a.getAddress2());
        Assert.assertEquals("Nagasaki", a.getDistrict());
        Assert.assertEquals("35200", a.getPostalCode());
        Assert.assertEquals("28303384290", a.getPhone());
        Assert.assertEquals(a.getCity(), cityRepository.get(463));
    }

    @Override
    @Test
    public void getAll() {
        List<Address> as = addressRepository.getAll();
        Assert.assertEquals(603, as.size());
        Address a = as.get(4);
        Assert.assertEquals("1913 Hanoi Way", a.getAddress());
        Assert.assertEquals("", a.getAddress2());
        Assert.assertEquals("Nagasaki", a.getDistrict());
        Assert.assertEquals("35200", a.getPostalCode());
        Assert.assertEquals("28303384290", a.getPhone());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> ids = IntStream.range(1, 201).boxed().collect(Collectors.toList());
        List<Address> as = addressRepository.getAllById(ids);
        Assert.assertEquals(200, as.size());
        Address a = as.get(4);
        Assert.assertEquals("1913 Hanoi Way", a.getAddress());
        Assert.assertEquals("", a.getAddress2());
        Assert.assertEquals("Nagasaki", a.getDistrict());
        Assert.assertEquals("35200", a.getPostalCode());
        Assert.assertEquals("28303384290", a.getPhone());
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf("603"), addressRepository.count());
    }

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(addressRepository.existsById(19));
        Assert.assertFalse(addressRepository.existsById(700));
    }

    @Override
    @Test
    public void update() {
        Address a = addressRepository.get(7);
        a.setAddress("151 Random");
        a.setAddress2("random");
        a.setDistrict("Downtown");
        a.setCity(cityRepository.get(5));
        a.setPostalCode("12345");
        a.setPhone("54321");
        addressRepository.update(a);

        Address a2 = addressRepository.get(7);
        Assert.assertEquals("151 Random", a2.getAddress());
        Assert.assertEquals("random", a2.getAddress2());
        Assert.assertEquals("Downtown", a2.getDistrict());
        Assert.assertEquals("12345", a2.getPostalCode());
        Assert.assertEquals("54321", a2.getPhone());
        Assert.assertEquals(cityRepository.get(5), a2.getCity());
    }
}
