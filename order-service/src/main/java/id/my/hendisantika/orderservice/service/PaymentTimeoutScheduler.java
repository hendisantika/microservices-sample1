package id.my.hendisantika.orderservice.service;

import id.my.hendisantika.orderservice.domain.Order;
import id.my.hendisantika.orderservice.domain.OrderEventType;
import id.my.hendisantika.orderservice.domain.OrderStatus;
import id.my.hendisantika.orderservice.domain.OutboxEvent;
import id.my.hendisantika.orderservice.repository.OrderRepository;
import id.my.hendisantika.orderservice.repository.OutboxRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

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

    @Scheduled(fixedDelay = 60000) // every minute
    @Transactional
    public void detectTimedOutPayments() {
        Instant cutoff = Instant.now().minus(PAYMENT_TIMEOUT);

        List<Order> timedOutOrders =
                orderRepo.findByStatusAndCreatedAtBefore(
                        OrderStatus.PAYMENT_PENDING,
                        cutoff
                );

        for (Order order : timedOutOrders) {

            order.markPaymentTimeout();

            outboxRepo.save(
                    OutboxEvent.create(
                            order.getId(),
                            OrderEventType.ORDER_PAYMENT_TIMEOUT,
                            Map.of(
                                    "orderId", order.getId().toString(),
                                    "reason", "PAYMENT_TIMEOUT"
                            )
                    )
            );
        }
    }
}
