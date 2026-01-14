package id.my.hendisantika.orderservice.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
 * Time: 05.41
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "outbox_events")
@Data
@AllArgsConstructor
public class OutboxEvent {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String aggregateType;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private UUID aggregateId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected OutboxEvent() {
    }

    public OutboxEvent(UUID aggregateId,
                       String eventType,
                       String payload) {

        this.id = UUID.randomUUID();
        this.aggregateType = "ORDER";
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = OutboxStatus.PENDING;
        this.createdAt = Instant.now();

    }

    /* ===============================
       FACTORY METHOD (IMPORTANT)
       =============================== */
    public static OutboxEvent create(
            UUID aggregateId,
            Enum<?> eventType,
            Object payload
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            return new OutboxEvent(
                    aggregateId,
                    eventType.name(),
                    mapper.writeValueAsString(payload)
            );

        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Failed to serialize outbox payload", e
            );
        }
    }
}
