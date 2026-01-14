package id.my.hendisantika.paymentservice.service;

import id.my.hendisantika.paymentservice.repository.PaymentLedgerRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 06.09
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PaymentProcessor {

    private final PaymentLedgerRepository repo;
    private final StringRedisTemplate redis;
    private final PaymentEventPublisher publisher;
    private final PaymentFailureService failureService;

    public PaymentProcessor(PaymentLedgerRepository repo, StringRedisTemplate redis, PaymentEventPublisher publisher, PaymentFailureService failureService) {
        this.repo = repo;
        this.redis = redis;
        this.publisher = publisher;
        this.failureService = failureService;
    }
}
