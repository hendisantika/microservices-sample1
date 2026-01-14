package id.my.hendisantika.paymentservice.service;

import id.my.hendisantika.paymentservice.repository.PaymentLedgerRepository;
import org.springframework.stereotype.Service;

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
}
