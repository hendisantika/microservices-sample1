package id.my.hendisantika.orderservice.repository;

import id.my.hendisantika.orderservice.domain.Order;
import id.my.hendisantika.orderservice.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.46
 * To change this template use File | Settings | File Templates.
 */
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findByIdempotencyKey(String key);

    List<Order> findByStatusAndCreatedAtBefore(
            OrderStatus status,
            Instant cutoff
    );
}
