package com.example.demo.kafka.client;

import com.example.demo.kafka.KafkaTestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lh0
 * @date 2021/7/16
 * @desc
 */

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaTestApplication.class)
@EmbeddedKafka(count = 3, ports = {9096, 9097,
    9098}, brokerPropertiesLocation = "classpath:broker.properties")
public class ConsumerClientTests {

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<Integer,String> kafkaTemplate;

    @Test
    public void test() throws InterruptedException {
        kafkaTemplate.send("templateTopic", "hello");

        Thread.sleep(3000);
    }

    @Test
    public void testDTL() throws InterruptedException {
        kafkaTemplate.send("kafka-topic2", "hello");

        Thread.sleep(3000);
    }
}
