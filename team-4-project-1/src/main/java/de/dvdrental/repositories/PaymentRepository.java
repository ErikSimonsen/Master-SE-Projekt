package de.dvdrental.repositories;

import de.dvdrental.entities.Payment;
import de.dvdrental.entities.Rental;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.ejb.Stateless;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;

@Named
@Stateless
public class PaymentRepository extends CrudRepository<Payment> {
    public PaymentRepository() {
        super(Payment.class);
    }

    public BigDecimal getPaymentsSumForRental(Rental rental) {
        return  (BigDecimal) em.createQuery("select SUM(p.amount) from Payment p where p.rental =:rental")
                               .setParameter("rental", rental).getSingleResult();
    }
}
