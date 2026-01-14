package id.my.hendisantika.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.orderservice.repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.48
 * To change this template use File | Settings | File Templates.
 */
@Component
public class OutboxEventPublisher {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafka;
    private final String topic;
    private final ObjectMapper objectMapper;

    public OutboxEventPublisher(OutboxRepository outboxRepository, KafkaTemplate<String, Object> kafka, @Value("${order.topic.name}") String topic, ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.kafka = kafka;
        this.topic = topic;
        this.objectMapper = objectMapper;
    }
}
