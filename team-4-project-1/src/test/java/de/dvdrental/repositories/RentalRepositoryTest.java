package de.dvdrental.repositories;

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
public class RentalRepositoryTest extends RepositoryTest {
    @Inject
    RentalRepository rentalRepository;
    @Inject
    CustomerRepository customerRepository;
    @Inject
    InventoryRepository inventoryRepository;
    @Inject
    StaffRepository staffRepository;

    @Override
    @Test
    public void createAndDelete() {
        Rental r = new Rental(fromString("2018-07-24 23:03:39"), fromString("2018-07-28 22:03:39"),
                inventoryRepository.get(176), customerRepository.get(51), staffRepository.get(1));
        rentalRepository.create(r);
        Rental r2 = rentalRepository.get(r.getRentalId());
        Assert.assertEquals(fromString("2018-07-24 23:03:39"), r2.getRentalDate());
        Assert.assertEquals(fromString("2018-07-28 22:03:39"), r2.getReturnDate());
        Assert.assertEquals(inventoryRepository.get(176), r2.getInventory());
        Assert.assertEquals(customerRepository.get(51), r2.getCustomer());
        Assert.assertEquals(staffRepository.get(1), r2.getStaff());
        Assert.assertTrue(rentalRepository.existsById(r.getRentalId()));

        rentalRepository.delete(r);
        Assert.assertFalse(rentalRepository.existsById(r2.getRentalId()));
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        Rental r1 = new Rental(fromString("2018-07-24 23:03:39"), fromString("2018-07-28 22:03:39"),
                inventoryRepository.get(176), customerRepository.get(51), staffRepository.get(1));
        Rental r2 = new Rental(fromString("2017-07-24 23:03:39"), fromString("2017-07-28 22:03:39"),
                inventoryRepository.get(100), customerRepository.get(51), staffRepository.get(1));
        List<Rental> rs = Arrays.asList(r1, r2);
        rentalRepository.createAll(rs);
        Assert.assertTrue(rentalRepository.existsById(r1.getRentalId()));
        Assert.assertTrue(rentalRepository.existsById(r2.getRentalId()));

        Rental r3 = rentalRepository.get(r1.getRentalId());
        Assert.assertEquals(fromString("2018-07-24 23:03:39"), r3.getRentalDate());
        Assert.assertEquals(fromString("2018-07-28 22:03:39"), r3.getReturnDate());
        Assert.assertEquals(inventoryRepository.get(176), r3.getInventory());
        Assert.assertEquals(customerRepository.get(51), r3.getCustomer());
        Assert.assertEquals(staffRepository.get(1), r3.getStaff());

        rentalRepository.deleteAll(rs);
        Assert.assertFalse(rentalRepository.existsById(r1.getRentalId()));
        Assert.assertFalse(rentalRepository.existsById(r2.getRentalId()));

    }

    public Timestamp fromString(String s){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
        Rental r = rentalRepository.get(8);
        Assert.assertEquals(r.getCustomer(), customerRepository.get(239));
        Assert.assertEquals(r.getInventory(), inventoryRepository.get(2346));
        Assert.assertEquals(r.getStaff(), staffRepository.get(2));
        Assert.assertEquals(fromString("2005-05-24 23:31:46"), r.getRentalDate());
        Assert.assertEquals(fromString("2005-05-27 23:33:46"), r.getReturnDate());
    }

    @Override
    @Test
    public void getAll() {
        Assert.assertEquals(16044, rentalRepository.getAll().size());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> ids = IntStream.range(1, 201).boxed().collect(Collectors.toList());
        List<Rental> rentals = rentalRepository.getAllById(ids);
        Rental r = rentals.get(10);
        Assert.assertEquals(11, r.getRentalId());
        Assert.assertEquals(r.getCustomer(), customerRepository.get(142));
        Assert.assertEquals(r.getInventory(), inventoryRepository.get(4443));
        Assert.assertEquals(r.getStaff(), staffRepository.get(2));
        Assert.assertEquals(fromString("2005-05-25 00:09:02"), r.getRentalDate());
        Assert.assertEquals(fromString("2005-06-02 20:56:02"), r.getReturnDate());
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf(16044), rentalRepository.count());
    }

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(rentalRepository.existsById(16000));
        Assert.assertFalse(rentalRepository.existsById(17000));
    }

    @Override
    @Test
    public void update() {
        Rental r = rentalRepository.get(3);
        r.setReturnDate(fromString("2015-05-24 23:03:39"));
        rentalRepository.update(r);
        Assert.assertEquals(fromString("2015-05-24 23:03:39"), rentalRepository.get(3).getReturnDate());
    }
}
