package id.my.hendisantika.paymentservice.repository;

import id.my.hendisantika.paymentservice.domain.PaymentLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 06.07
 * To change this template use File | Settings | File Templates.
 */
public interface PaymentLedgerRepository extends JpaRepository<PaymentLedger, UUID> {
    Optional<PaymentLedger> findByOrderId(UUID orderId);

    boolean existsByOrderId(UUID orderId);
}
