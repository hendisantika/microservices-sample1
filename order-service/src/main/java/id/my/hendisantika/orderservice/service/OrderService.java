package id.my.hendisantika.orderservice.service;

import id.my.hendisantika.orderservice.domain.Order;
import id.my.hendisantika.orderservice.domain.OutboxEvent;
import id.my.hendisantika.orderservice.repository.OrderRepository;
import id.my.hendisantika.orderservice.repository.OutboxRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
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
 * Time: 05.47
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public UUID createOrder(String customerId, String idemKey) {

        // First-level idempotency (fast path)
        var existing = orderRepository.findByIdempotencyKey(idemKey);
        if (existing.isPresent()) {
            return existing.get().getId();
        }

        try {
            UUID orderId = UUID.randomUUID();

            Order order =
                    new Order(orderId, customerId, idemKey);
            orderRepository.save(order);

            OutboxEvent event =
                    new OutboxEvent(
                            orderId,
                            "ORDER_CREATED",
                            buildPayload(orderId, customerId)
                    );

            outboxRepository.save(event);

            return orderId;

        } catch (DataIntegrityViolationException ex) {
            // Second-level idempotency (race-safe)
            return orderRepository.findByIdempotencyKey(idemKey)
                    .map(Order::getId)
                    .orElseThrow();
        }


    }
}
