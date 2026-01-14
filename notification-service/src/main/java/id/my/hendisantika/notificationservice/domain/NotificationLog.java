package id.my.hendisantika.notificationservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "notification_log",
        uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "type"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationLog {
    @Id
    private UUID id;


    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Instant sentAt;

    public NotificationLog(UUID orderId, NotificationType type) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.type = type;
        this.sentAt = Instant.now();
    }
}
