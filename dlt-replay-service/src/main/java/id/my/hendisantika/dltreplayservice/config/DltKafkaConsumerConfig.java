package id.my.hendisantika.dltreplayservice.config;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.23
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class DltKafkaConsumerConfig {

    @Bean
    public KafkaConsumer<String, String> dltKafkaConsumer(
            KafkaProperties kafkaProperties
    ) {
        Properties props = new Properties();

        props.putAll(kafkaProperties.buildConsumerProperties());

        // 🔑 Dedicated consumer group for admin replay
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "dlt-replay-admin");

        // 🔑 Required for manual seek
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        return new KafkaConsumer<>(props);
    }
}
