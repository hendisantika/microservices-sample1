package id.my.hendisantika.orderservice.orderconsumer;

import id.my.hendisantika.orderservice.domain.Order;
import id.my.hendisantika.orderservice.domain.OrderStatus;
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
 * Time: 05.44
 * To change this template use File | Settings | File Templates.
 */
@Component
public class OrderTimeoutConsumer {

    private final OrderRepository orderRepo;

    public OrderTimeoutConsumer(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @KafkaListener(
            topics = "order.payment.timeout",
            groupId = "order-service"
    )
    @Transactional
    public void handleTimeout(Map<String, Object> event) {

        UUID orderId =
                UUID.fromString(event.get("orderId").toString());

        Order order = orderRepo.findById(orderId)
                .orElseThrow();

        // Idempotency
        if (order.getStatus() == OrderStatus.PAYMENT_TIMEOUT ||
                order.getStatus() == OrderStatus.CANCELLED) {
            return;
        }

        order.cancel();
    }
}
