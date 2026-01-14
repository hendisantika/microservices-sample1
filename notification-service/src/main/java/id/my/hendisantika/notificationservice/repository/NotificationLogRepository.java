package id.my.hendisantika.notificationservice.repository;

import id.my.hendisantika.notificationservice.domain.NotificationLog;
import id.my.hendisantika.notificationservice.domain.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface NotificationLogRepository extends JpaRepository<NotificationLog, UUID> {

    boolean existsByOrderIdAndType(UUID orderId, NotificationType type);
}
