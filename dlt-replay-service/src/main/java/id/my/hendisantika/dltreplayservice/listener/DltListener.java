package id.my.hendisantika.dltreplayservice.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.25
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DltListener {

    private final DltReplayService replayService;

    public DltListener(DltReplayService replayService) {
        this.replayService = replayService;
    }

    @KafkaListener(
            topics = {
                    "order.created.dlt",
                    "payment.failed.dlt",
                    "payment.completed.dlt",
                    "notification.dlt"
            },
            groupId = "dlt-replay-admin"
    )
    public void consume(ConsumerRecord<String, String> record) {

        System.out.println("DLT message received:");
        System.out.println(record.value());

        // Replay is MANUAL via API, not automatic
    }
}
