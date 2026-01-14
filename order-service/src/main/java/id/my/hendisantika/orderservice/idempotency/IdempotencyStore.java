package id.my.hendisantika.orderservice.idempotency;

import id.my.hendisantika.orderservice.domain.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.44
 * To change this template use File | Settings | File Templates.
 */
@Component
public class IdempotencyStore {

    private final OrderRepository orderRepository;

    public IdempotencyStore(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> findExisting(String key) {
        return orderRepository.findByIdempotencyKey(key);
    }
}
