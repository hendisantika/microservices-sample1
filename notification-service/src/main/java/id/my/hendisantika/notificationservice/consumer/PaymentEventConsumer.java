package id.my.hendisantika.notificationservice.consumer;

import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.31
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PaymentEventConsumer {

    private final NotificationLogRepository repo;
    private final NotificationSender sender;

    public PaymentEventConsumer(
            NotificationLogRepository repo,
            NotificationSender sender
    ) {
        this.repo = repo;
        this.sender = sender;
    }

}
