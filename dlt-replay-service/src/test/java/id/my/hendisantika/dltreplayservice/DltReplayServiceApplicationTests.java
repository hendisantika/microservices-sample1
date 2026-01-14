package id.my.hendisantika.dltreplayservice;

import id.my.hendisantika.dltreplayservice.config.DltKafkaConsumerConfig;
import id.my.hendisantika.dltreplayservice.listener.DltListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DltReplayServiceApplicationTests {

    @MockBean
    private DltKafkaConsumerConfig dltKafkaConsumerConfig;

    @MockBean
    private DltListener dltListener;

    @Test
    void contextLoads() {
    }

}
