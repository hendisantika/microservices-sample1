package id.my.hendisantika.orderservice.service;

import id.my.hendisantika.orderservice.repository.OrderRepository;
import id.my.hendisantika.orderservice.repository.OutboxRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.49
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PaymentTimeoutScheduler {

    private static final Duration PAYMENT_TIMEOUT = Duration.ofMinutes(5);

    private final OrderRepository orderRepo;
    private final OutboxRepository outboxRepo;

    public PaymentTimeoutScheduler(
            OrderRepository orderRepo,
            OutboxRepository outboxRepo
    ) {
        this.orderRepo = orderRepo;
        this.outboxRepo = outboxRepo;
    }
}
