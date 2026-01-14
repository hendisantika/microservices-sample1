package id.my.hendisantika.orderservice.paymentconsumer;

import id.my.hendisantika.orderservice.domain.Order;
import id.my.hendisantika.orderservice.domain.OrderStatus;
import id.my.hendisantika.orderservice.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PaymentEventConsumer {

    private final OrderRepository repository;


    public PaymentEventConsumer(OrderRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = {
                    "payment.completed",
                    "payment.failed"
            },
            groupId = "order-microservice"
    )
    @Transactional
    public void handlePaymentEvent(Map<String, Object> event) {

        UUID orderId = UUID.fromString(event.get("orderId").toString());
        String status = event.get("status").toString();

        Order order = repository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalStateException("Order not found: " + orderId)
                );

        // ---- Idempotency guard (important)
        if (order.getStatus() == OrderStatus.PAID
                || order.getStatus() == OrderStatus.PAYMENT_FAILED) {
            return;
        }

        if ("SUCCESS".equals(status)) {
            order.markPaid();
        } else {
            order.markPaymentFailed();
        }

        repository.save(order);
    }
}
