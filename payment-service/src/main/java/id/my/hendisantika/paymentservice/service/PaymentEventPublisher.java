package id.my.hendisantika.paymentservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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

}
