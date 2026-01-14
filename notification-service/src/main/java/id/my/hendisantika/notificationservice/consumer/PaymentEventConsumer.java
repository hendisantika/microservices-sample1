package id.my.hendisantika.notificationservice.consumer;

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

    @KafkaListener(
            topics = {"payment.completed", "payment.failed", "order.payment.timeout"},
            groupId = "notification-microservice"
    )
    @Transactional
    public void consume(Map<String, Object> event) {
        System.out.println("Notification for order " + event);

        UUID orderId =
                UUID.fromString(event.get("orderId").toString());

        String status =
                event.getOrDefault("status", "TIMEOUT").toString();

        NotificationType type;

        switch (status) {
            case "SUCCESS" -> {
                type = NotificationType.PAYMENT_SUCCESS;
                sender.sendSuccess(orderId);
            }
            case "FAILED" -> {
                type = NotificationType.PAYMENT_FAILED;
                sender.sendFailure(orderId);
            }
            default -> {
                type = NotificationType.PAYMENT_TIMEOUT;
                sender.sendPaymentTimeout(orderId);
            }
        }

        if (repo.existsByOrderIdAndType(orderId, type)) {
            return;
        }

        repo.save(new NotificationLog(orderId, type));
    }
}
