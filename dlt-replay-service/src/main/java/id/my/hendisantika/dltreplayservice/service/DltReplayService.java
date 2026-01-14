package id.my.hendisantika.dltreplayservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.26
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DltReplayService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DltReplayService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Replay a single DLT payload to original topic
     */
    public void replay(
            ConsumerRecord<String, String> record
    ) {

        Header originalTopicHeader =
                record.headers().lastHeader("x-original-topic");

        if (originalTopicHeader == null) {
            throw new IllegalStateException(
                    "Missing x-original-topic header"
            );
        }

        String originalTopic =
                new String(originalTopicHeader.value(), StandardCharsets.UTF_8);

        kafkaTemplate.send(
                originalTopic,
                record.key(),
                record.value()
        );
    }
}
