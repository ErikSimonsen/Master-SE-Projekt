package de.dvdrental.repositories;

import de.dvdrental.entities.Payment;
import de.dvdrental.entities.Rental;
import de.dvdrental.repositories.interfaces.RepositoryTest;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(Arquillian.class)
public class PaymentRepositoryTest extends RepositoryTest {

    @Inject
    PaymentRepository paymentRepository;
    @Inject
    CustomerRepository customerRepository;
    @Inject
    StaffRepository staffRepository;
    @Inject
    InventoryRepository inventoryRepository;
    @Inject
    RentalRepository rentalRepository;

    @Override
    @Test
    public void createAndDelete() {
        Rental r = new Rental(inventoryRepository.get(176), customerRepository.get(51), staffRepository.get(1));
        rentalRepository.create(r);
        Payment p = new Payment(new BigDecimal("4.67"), customerRepository.get(51), staffRepository.get(1), r);
        paymentRepository.create(p);

        Payment p2 = paymentRepository.get(p.getPaymentId());
        Assert.assertEquals(p2.getCustomer(), customerRepository.get(51));
        Assert.assertEquals(p2.getStaff(), staffRepository.get(1));
        Assert.assertEquals(p2.getAmount(), new BigDecimal("4.67"));

        paymentRepository.delete(p);
        rentalRepository.delete(r);
    }

    @Override
    @Test
    public void createAllAndDeleteAll() {
        Rental r1 = new Rental(inventoryRepository.get(176), customerRepository.get(51), staffRepository.get(1));
        Rental r2 = new Rental(inventoryRepository.get(175), customerRepository.get(52), staffRepository.get(2));
        rentalRepository.create(r1);
        rentalRepository.create(r2);
        Payment p1 = new Payment(new BigDecimal("4.67"), customerRepository.get(51), staffRepository.get(1), r1);
        Payment p2 = new Payment(new BigDecimal("4.77"), customerRepository.get(52), staffRepository.get(2), r2);
        List<Payment> ps = Arrays.asList(p1, p2);
        paymentRepository.createAll(ps);

        Assert.assertEquals(paymentRepository.get(p1.getPaymentId()), p1);
        Assert.assertEquals(paymentRepository.get(p2.getPaymentId()), p2);

        paymentRepository.deleteAll(ps);
        Assert.assertNull(paymentRepository.get(p1.getPaymentId()));
        Assert.assertNull(paymentRepository.get(p2.getPaymentId()));
        rentalRepository.delete(r1);
        rentalRepository.delete(r2);
    }


    @Override
    @Test
    public void get() {
        Assert.assertNull(paymentRepository.get(3));

        Payment p = paymentRepository.get(17504);
        Assert.assertEquals(p.getPaymentId(), 17504);
        Assert.assertEquals(p.getCustomer(), customerRepository.get(341));
        Assert.assertEquals(p.getStaff(), staffRepository.get(1));
        Assert.assertEquals(new BigDecimal("1.99"), p.getAmount());
        //Assert.assertEquals("2007-02-15 22:25:46", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(p.getPaymentDate()));
    }

    @Override
    @Test
    public void getAll() {
        Assert.assertEquals(14596, paymentRepository.getAll().size());
    }

    @Override
    @Test
    public void getAllById() {
        List<Integer> ids = IntStream.range(17503, 17603).boxed().collect(Collectors.toList());
        List<Payment> ps = paymentRepository.getAllById(ids);
        Assert.assertEquals(100, ps.size());
        Assert.assertEquals(paymentRepository.get(17503), ps.get(0));
        Assert.assertEquals(17503, ps.get(0).getPaymentId());
    }

    @Override
    @Test
    public void count() {
        Assert.assertEquals(Long.valueOf("14596"), paymentRepository.count());
    }

    @Override
    @Test
    public void existsById() {
        Assert.assertTrue(paymentRepository.existsById(17600));
        Assert.assertFalse(paymentRepository.existsById(5));
    }

    @Override
    @Test
    public void update() {
        Payment p = paymentRepository.get(17503);
        p.setAmount(new BigDecimal("4.81"));
        paymentRepository.update(p);
        Assert.assertEquals(p, paymentRepository.get(17503));
        Assert.assertEquals(new BigDecimal("4.81"), paymentRepository.get(17503).getAmount());
    }

    @Test
    public void getPaymentsSumForRental() {
        Rental r = rentalRepository.get(4326);
        Assert.assertEquals(new BigDecimal("10.99"), paymentRepository.getPaymentsSumForRental(r));
        Payment p = new Payment(new BigDecimal("1.68"), r.getCustomer(), staffRepository.get(1), r);
        paymentRepository.create(p);
        Assert.assertEquals(new BigDecimal("12.67"), paymentRepository.getPaymentsSumForRental(r));
        paymentRepository.delete(p);
    }
}
