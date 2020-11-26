package de.dvdrental.services;

import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Payment;
import de.dvdrental.entities.Rental;
import de.dvdrental.jsfBeans.CustomerRentalsService;
import de.dvdrental.repositories.CustomerRepository;
import de.dvdrental.repositories.PaymentRepository;
import de.dvdrental.repositories.RentalRepository;
import de.dvdrental.repositories.StaffRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@RunWith(Arquillian.class)
public class CustomerRentalsServiceTest {
    @Inject
    private CustomerRentalsService customerRentalsService;
    @Inject
    private RentalRepository rentalRepository;
    @Inject
    private StaffRepository staffRepository;
    @Inject
    private PaymentRepository paymentRepository;
    @Inject
    private CustomerRepository customerRepository;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "de.dvdrental")
                .addAsResource("META-INF/persistence_test.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void getCostsPaid() {
        Rental r1 = rentalRepository.get(20);
        Assert.assertEquals(BigDecimal.ZERO, customerRentalsService.getCostsPaid(r1));
        Rental r2 = rentalRepository.get(1194);
        Assert.assertEquals(new BigDecimal("4.99"), customerRentalsService.getCostsPaid(r2));
        Payment p = new Payment(new BigDecimal("1.85"), r2.getCustomer(), staffRepository.get(1), r2);
        paymentRepository.create(p);
        Assert.assertEquals(new BigDecimal("6.84"), customerRentalsService.getCostsPaid(r2));
        paymentRepository.delete(p);
    }

    @Test
    public void getFine() {
        Rental r = rentalRepository.get(76);
        Assert.assertEquals(BigDecimal.valueOf(3), customerRentalsService.getFine(r));
        Rental r2 = rentalRepository.get(4611);
        Assert.assertEquals(BigDecimal.valueOf(2), customerRentalsService.getFine(r2));
        Rental r3 = rentalRepository.get(1476);
        Assert.assertEquals(new BigDecimal("20.99"), customerRentalsService.getFine(r3));
        Assert.assertEquals(new BigDecimal("20.99"), customerRentalsService.getLimitedFine(r3));

        //Now we test the fine of an open rental
        Rental r4 = rentalRepository.get(12682);
        Assert.assertNull(r4.getReturnDate());
        int numDays = (int) Math.ceil((System.currentTimeMillis() - r4.getRentalDate().getTime()) / (1000.0 * 60 * 60 * 24));
        BigDecimal shouldPay = BigDecimal.valueOf(numDays)
                                         .add(r4.getInventory().getFilm().getReplacementCost())
                                         .subtract(BigDecimal.valueOf(r4.getInventory().getFilm().getRentalDuration()));
        Assert.assertEquals(shouldPay, customerRentalsService.getFine(r4));
        Assert.assertEquals(new BigDecimal("997.00"), customerRentalsService.getLimitedFine(r4));
    }

    @Test
    public void getCosts() {
        Rental r = rentalRepository.get(76);
        Assert.assertEquals(new BigDecimal("3.99"), customerRentalsService.getCosts(r));
        Rental r2 = rentalRepository.get(4611);
        Assert.assertEquals(new BigDecimal("6.99"), customerRentalsService.getCosts(r2));
        Rental r3 = rentalRepository.get(1476);
        Assert.assertEquals(new BigDecimal("23.98"), customerRentalsService.getCosts(r3));
    }

    @Test
    public void isPaid() {
        Assert.assertFalse(customerRentalsService.isPaid(rentalRepository.get(2345)));
        Assert.assertTrue(customerRentalsService.isPaid(rentalRepository.get(13403)));
    }

    @Test
    public void getCostsLeftToPay() {
        Rental r = rentalRepository.get(2623);
        Assert.assertEquals(0, BigDecimal.ONE.compareTo(customerRentalsService.getCostsLeftToPay(r)));
        Rental r2 = rentalRepository.get(9627);
        Assert.assertEquals(0, BigDecimal.ZERO.compareTo(customerRentalsService.getCostsLeftToPay(r2)));
        Rental r3 = rentalRepository.get(10392);
        Assert.assertEquals(new BigDecimal("10.99"), customerRentalsService.getCostsLeftToPay(r3));
    }

    @Test
    public void getOverallCostsLeftToPay() {
        Customer customer = customerRepository.get(17);
        List<Rental> rentals = customerRepository.getRentals(customer);
        Assert.assertEquals(new BigDecimal("17.97"), customerRentalsService.getOverallCostsLeftToPay(rentals));
        Customer customer2 = customerRepository.get(24);
        List<Rental> rentals2 = customerRepository.getRentals(customer2);
        Assert.assertEquals(new BigDecimal("13.98"), customerRentalsService.getOverallCostsLeftToPay(rentals2));
    }
}
