package de.dvdrental.repositories;

import de.dvdrental.entities.Payment;
import de.dvdrental.repositories.interfaces.CrudRepository;

import javax.enterprise.context.RequestScoped;
import java.math.BigDecimal;

@RequestScoped
public class PaymentRepository extends CrudRepository<Payment> {
    public PaymentRepository() {
        super(Payment.class);
    }

    public BigDecimal getPaymentsSumForRental(Integer rentalId) {
        return  em.createQuery("select SUM(p.amount) from Payment p where p.rentalId =:rentalId", BigDecimal.class)
                  .setParameter("rentalId", rentalId)
                  .getSingleResult();
    }
}
