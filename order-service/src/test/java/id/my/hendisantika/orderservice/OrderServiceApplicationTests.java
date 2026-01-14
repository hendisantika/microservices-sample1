package id.my.hendisantika.orderservice;

import id.my.hendisantika.orderservice.config.KafkaConsumerConfig;
import id.my.hendisantika.orderservice.orderconsumer.OrderTimeoutConsumer;
import id.my.hendisantika.orderservice.paymentconsumer.PaymentEventConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class OrderServiceApplicationTests {

    @MockBean
    private KafkaConsumerConfig kafkaConsumerConfig;

    @MockBean
    private OrderTimeoutConsumer orderTimeoutConsumer;

    @MockBean
    private PaymentEventConsumer paymentEventConsumer;

    @Test
    void contextLoads() {
    }

}
