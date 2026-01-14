package id.my.hendisantika.notificationservice.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.33
 * To change this template use File | Settings | File Templates.
 */
@Service
public class NotificationSender {

    public void sendSuccess(UUID orderId) {
        System.out.println("Payment SUCCESS notification sent for order " + orderId);
    }

    public void sendFailure(UUID orderId) {
        System.out.println("Payment FAILED notification sent for order " + orderId);
    }

    public void sendPaymentTimeout(UUID orderId) {
        System.out.println(
                "Payment TIMEOUT notification sent for order " + orderId
        );
    }
}
