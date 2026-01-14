package id.my.hendisantika.orderservice.domain;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.42
 * To change this template use File | Settings | File Templates.
 */
public record PaymentEvent(UUID orderId,
                           String status) {
}
