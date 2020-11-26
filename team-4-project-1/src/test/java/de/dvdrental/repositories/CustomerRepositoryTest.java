package de.dvdrental.repositories;

import de.dvdrental.entities.Address;
import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Rental;
import de.dvdrental.repositories.interfaces.RepositoryTest;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(Arquillian.class)
public class CustomerRepositoryTest extends RepositoryTest {
    @Inject
    CustomerRepository customerRepository;
    @Inject
    AddressRepository addressRepository;
    @Inject
    StoreRepository storeRepository;
    @Inject
    RentalRepository rentalRepository;

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(customerRepository.existsById(1));
    }

    public Timestamp fromString(String s){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(s);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Test
    public void get() {
        Customer c = customerRepository.get(18);
        Assert.assertEquals(18, c.getCustomerId());
        Assert.assertEquals("Carol", c.getFirstName());
        Assert.assertEquals("Garcia", c.getLastName());
        Assert.assertEquals("carol.garcia@sakilacustomer.org", c.getEmail());
        Assert.assertTrue(c.isActivebool());
        Assert.assertEquals(fromString("2006-02-14"), c.getCreateDate());
        Assert.assertEquals(Integer.valueOf(1), c.getActive());
        Assert.assertEquals(addressRepository.get(22), c.getAddress());
        Assert.assertEquals("320 Brest Avenue", c.getAddress().getAddress());
        Assert.assertEquals(storeRepository.get(2), c.getStore());
    }

    @Override
    @Test
    public void getAll() {
        Assert.assertEquals(599, customerRepository.getAll().size());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> list = IntStream.range(1, 16).boxed().collect(Collectors.toList());
        List<Customer> customerList = customerRepository.getAllById(list);
        Assert.assertEquals(customerRepository.get(1), customerList.get(0));
        Assert.assertEquals(customerRepository.get(15), customerList.get(14));
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf(599), customerRepository.count());
    }

    @Override
    @Test
    public void update() {
        Customer customer = customerRepository.get(1);
        customer.setFirstName("Julia");
        customer.setLastName("Mustermann");
        customer.setActivebool(false);
        customer.setActive(0);
        customer.setEmail("test@random.com");
        customer.setAddress(addressRepository.get(10));
        customer.setStore(storeRepository.get(2));
        customerRepository.update(customer);
        Customer customer2 = customerRepository.get(1);
        Assert.assertEquals("Julia", customer2.getFirstName());
        Assert.assertEquals("Mustermann", customer2.getLastName());
        Assert.assertFalse(customer2.isActivebool());
        Assert.assertEquals(Integer.valueOf(0), customer2.getActive());
        Assert.assertEquals("test@random.com", customer2.getEmail());
        Assert.assertEquals(customer2.getAddress(), addressRepository.get(10));
        Assert.assertEquals("1795 Santiago de Compostela Way", customer2.getAddress().getAddress());
        Assert.assertEquals(storeRepository.get(2), customer2.getStore());
    }

    @Override
    @Test
    public void createAndDelete() {
        Customer c = new Customer(storeRepository.get(1), "Lina", "Mustermann", "lina@m.gmail.com", true, 1, addressRepository.get(228));
        customerRepository.create(c);
        Customer c2 = customerRepository.get(c.getCustomerId());

        Assert.assertEquals("Lina", c2.getFirstName());
        Assert.assertEquals("Mustermann", c2.getLastName());
        Assert.assertEquals("lina@m.gmail.com", c2.getEmail());
        Assert.assertTrue(c2.isActivebool());
        Assert.assertEquals(Integer.valueOf(1), c2.getActive());
        Assert.assertEquals(addressRepository.get(228), c2.getAddress());
        Assert.assertEquals("60 Poos de Caldas Street", c2.getAddress().getAddress());
        Assert.assertEquals(storeRepository.get(1), c2.getStore());
        Assert.assertEquals(Long.valueOf(600), customerRepository.count());

        customerRepository.delete(c);
        Assert.assertNull(customerRepository.get(c.getCustomerId()));
        Assert.assertFalse(customerRepository.existsById(c.getCustomerId()));
        Assert.assertEquals(Long.valueOf(599), customerRepository.count());
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        Customer c1 = new Customer(storeRepository.get(1), "Mara", "Mustermann", "m@m.gmail.com", true, 1, addressRepository.get(230));
        Customer c2 = new Customer(storeRepository.get(1), "Mira", "Mustermann", "m@m.gmail.com", true, 1, addressRepository.get(231));
        List<Customer> customers = Arrays.asList(c1, c2);
        customerRepository.createAll(customers);
        Assert.assertEquals(c1, customerRepository.get(c1.getCustomerId()));
        Assert.assertEquals(c2, customerRepository.get(c2.getCustomerId()));
        Assert.assertEquals(Long.valueOf(601), customerRepository.count());

        customerRepository.deleteAll(customers);
        Assert.assertNull(customerRepository.get(c1.getCustomerId()));
        Assert.assertNull(customerRepository.get(c2.getCustomerId()));
        Assert.assertEquals(Long.valueOf(599), customerRepository.count());
    }

    @Test
    public void getNumRentals() {
        Customer c = customerRepository.get(1);
        Assert.assertEquals(32, customerRepository.getNumRentals(c));
        Assert.assertTrue(customerRepository.hasAnyRentals(c));

        Customer c2 = customerRepository.get(6);
        Assert.assertEquals(28, customerRepository.getNumRentals(c2));
        Assert.assertTrue(customerRepository.hasAnyRentals(c2));
    }

    @Test
    public void getRentals() {
        Customer customer = customerRepository.get(3);
        List<Rental> rentals = customerRepository.getRentals(customer);
        Assert.assertEquals(26, rentals.size());
        Assert.assertEquals(rentalRepository.get(435), rentals.get(0));
        Assert.assertEquals(rentalRepository.get(15619), rentals.get(25));
    }

    @Test
    public void getNumOpenRentals() {
        Assert.assertEquals(0, customerRepository.getNumOpenRentals(customerRepository.get(24)));
        Assert.assertEquals(1, customerRepository.getNumOpenRentals(customerRepository.get(245)));
        Assert.assertEquals(3, customerRepository.getNumOpenRentals(customerRepository.get(75)));
        Assert.assertEquals(2, customerRepository.getNumOpenRentals(customerRepository.get(267)));
        Assert.assertEquals(0, customerRepository.getNumOpenRentals(customerRepository.get(113)));
        Assert.assertTrue(customerRepository.hasOpenRental(customerRepository.get(267)));
        Assert.assertFalse(customerRepository.hasOpenRental(customerRepository.get(113)));
    }
}
