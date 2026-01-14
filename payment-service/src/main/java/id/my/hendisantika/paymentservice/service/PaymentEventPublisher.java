package id.my.hendisantika.paymentservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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
 * Time: 06.07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PaymentEventPublisher {
    private final KafkaTemplate<Object, Object> kafka;
    private final String completed;
    private final String failed;

    public PaymentEventPublisher(
            KafkaTemplate<Object, Object> kafka,
            @Value("${payment.topic.completed}") String completed,
            @Value("${payment.topic.failed}") String failed
    ) {
        this.kafka = kafka;
        this.completed = completed;
        this.failed = failed;
    }

    public void publishCompleted(UUID orderId) {
        kafka.send(completed, orderId.toString(),
                Map.of("orderId", orderId, "status", "SUCCESS"));
    }

}
