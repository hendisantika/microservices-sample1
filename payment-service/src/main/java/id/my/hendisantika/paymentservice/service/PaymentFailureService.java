package id.my.hendisantika.paymentservice.service;

import id.my.hendisantika.paymentservice.domain.PaymentLedger;
import id.my.hendisantika.paymentservice.domain.PaymentStatus;
import id.my.hendisantika.paymentservice.repository.PaymentLedgerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 06.08
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PaymentFailureService {

    private final PaymentLedgerRepository repo;
    private final PaymentEventPublisher publisher;

    public PaymentFailureService(PaymentLedgerRepository repo, PaymentEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordFailure(UUID orderId) {

        // Idempotency guard
        if (repo.existsByOrderId(orderId)) {
            return;
        }

        repo.save(new PaymentLedger(orderId, PaymentStatus.FAILED));
        publisher.publishFailed(orderId);
    }
}
