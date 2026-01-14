package id.my.hendisantika.orderservice.domain;

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
public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED
}
