package de.dvdrental.repositories;

import de.dvdrental.entities.Address;
import de.dvdrental.entities.Staff;
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
public class StaffRepositoryTest extends RepositoryTest {
    @Inject
    StaffRepository staffRep;
    @Inject
    AddressRepository addressRep;
    @Inject
    StoreRepository storeRepo;

    @Override
    @Test
    public void createAndDelete() {
        //First creating
        Address ad = addressRep.get(228);
        Staff staff = new Staff();
        staff.setFirstName("Max");
        staff.setLastName("Mustermann");
        staff.setEmail("max.mustermann@gmail.de");
        staff.setStore(storeRepo.get(1));
        staff.setActive(true);
        staff.setUsername("MaxMus");
        staff.setPassword("12345678910");
        staff.setAddress(ad);
        staffRep.create(staff);

        Staff staff2 = staffRep.get(staff.getStaffId());
        Assert.assertEquals("Max", staff2.getFirstName());
        Assert.assertEquals("Mustermann", staff2.getLastName());
        Assert.assertEquals("max.mustermann@gmail.de", staff2.getEmail());
        Assert.assertEquals(storeRepo.get(1), staff2.getStore());
        Assert.assertTrue(staff2.isActive());
        Assert.assertEquals("MaxMus", staff2.getUsername());
        Assert.assertEquals("12345678910", staff2.getPassword());
        Assert.assertEquals(ad, staff2.getAddress());

        //now deleting
        staffRep.delete(staff);
        Assert.assertNull(staffRep.get(staff.getStaffId()));
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        Address ad = addressRep.get(220);
        Address ad2 = addressRep.get(215);
        Staff staff = new Staff();
        staff.setFirstName("Max");
        staff.setLastName("Mustermann");
        staff.setEmail("max.mustermann@gmail.de");
        staff.setStore(storeRepo.get(1));
        staff.setActive(true);
        staff.setUsername("MaxMus");
        staff.setPassword("igfjndigjnfijngiof");
        staff.setAddress(ad);
        Staff staff2 = new Staff();
        staff2.setFirstName("Mia");
        staff2.setLastName("Mustermann");
        staff2.setEmail("mia.mustermann@gmail.de");
        staff2.setStore(storeRepo.get(1));
        staff2.setActive(true);
        staff2.setUsername("MiaMus");
        staff2.setPassword("123456545454541");
        staff2.setAddress(ad2);

        List<Staff> actors = Arrays.asList(staff, staff2);
        staffRep.createAll(actors);

        Assert.assertEquals(staff, staffRep.get(staff.getStaffId()));
        Assert.assertEquals(staff2, staffRep.get(staff2.getStaffId()));
        staffRep.deleteAll(actors);
        Assert.assertFalse(staffRep.existsById(staff.getStaffId()));
        Assert.assertFalse(staffRep.existsById(staff2.getStaffId()));
    }

    @Override
    @Test
    public void get() {
        Staff staff = staffRep.get(2);
        Assert.assertEquals(2, staff.getStaffId());
        Assert.assertEquals("Jon", staff.getFirstName());
        Assert.assertEquals("Stephens", staff.getLastName());
    }

    @Override
    @Test
    public void getAll() {
        Assert.assertEquals(2, staffRep.getAll().size());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> ids = IntStream.range(1, 3).boxed().collect(Collectors.toList());
        List<Staff> staffList = staffRep.getAllById(ids);
        Assert.assertEquals(2, staffList.size());

        Staff staff1 = staffList.get(1);
        Assert.assertEquals(2, staff1.getStaffId());
        Assert.assertEquals("Jon", staff1.getFirstName());
        Assert.assertEquals("Stephens", staff1.getLastName());
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf(2), staffRep.count());
    }

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(staffRep.existsById(1));
        Assert.assertFalse(staffRep.existsById(252));
    }

    @Override
    @Test
    public void update() {
        //First save
        Address ad = addressRep.get(210);
        Staff staff = new Staff();
        staff.setFirstName("Max");
        staff.setLastName("Mustermann");
        staff.setEmail("max.mustermann@gmail.de");
        staff.setStore(storeRepo.get(1));
        staff.setActive(true);
        staff.setUsername("MaxMus");
        staff.setPassword("1234576987154");
        staff.setAddress(ad);

        staffRep.create(staff);
        Assert.assertEquals(staff, staffRep.get(staff.getStaffId()));

        //now updating
        staff.setFirstName("Lisa");
        staffRep.update(staff);
        Assert.assertEquals("Lisa", staff.getFirstName());
        Assert.assertEquals("Lisa", staffRep.get(staff.getStaffId()).getFirstName());

        staff.setFirstName("Max");
        staffRep.update(staff);
        Assert.assertEquals("Max", staff.getFirstName());
        Assert.assertEquals("Max", staffRep.get(staff.getStaffId()).getFirstName());

        //now deleting
        staffRep.delete(staff);
        Assert.assertNull(staffRep.get(staff.getStaffId()));
    }
}
