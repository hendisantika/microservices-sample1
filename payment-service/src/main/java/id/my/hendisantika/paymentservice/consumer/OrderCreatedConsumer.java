package id.my.hendisantika.paymentservice.consumer;

import id.my.hendisantika.paymentservice.service.PaymentProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

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
 * Time: 06.05
 * To change this template use File | Settings | File Templates.
 */
@Component
public class OrderCreatedConsumer {

    private final PaymentProcessor processor;

    public OrderCreatedConsumer(PaymentProcessor processor) {
        this.processor = processor;
    }


    @KafkaListener(
            topics = "order.created",
            groupId = "payment-microservice",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            Map<String, Object> event,
            Acknowledgment ack
    ) {
        UUID orderId = UUID.fromString(event.get("orderId").toString());

        processor.processPayment(orderId);

        ack.acknowledge();
    }
}
