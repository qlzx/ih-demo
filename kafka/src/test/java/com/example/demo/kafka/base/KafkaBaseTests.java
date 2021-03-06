
package com.example.demo.kafka.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasKey;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasPartition;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.example.demo.kafka.embed.EmbeddedKafkaHolder;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

public class KafkaBaseTests {

    public static final String TEMPLATE_TOPIC = "templateTopic";

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TEMPLATE_TOPIC);

    @Test
    public void testTemplate() throws Exception {
        EmbeddedKafkaHolder.getEmbeddedKafka().addTopics(TEMPLATE_TOPIC);

        final BlockingQueue<ConsumerRecord<Integer, String>> records = container();

        Map<String, Object> producerProps =
                            KafkaTestUtils.producerProps(EmbeddedKafkaHolder.getEmbeddedKafka());
        ProducerFactory<Integer, String> pf =
                            new DefaultKafkaProducerFactory<Integer, String>(producerProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(TEMPLATE_TOPIC);
        template.sendDefault("foo");
        assertThat(records.poll(10, TimeUnit.SECONDS), hasValue("foo"));
        template.sendDefault(0, 2, "bar");
        ConsumerRecord<Integer, String> received = records.poll(10, TimeUnit.SECONDS);
        assertThat(received, hasKey(2));
        assertThat(received, hasPartition(0));
        assertThat(received, hasValue("bar"));
        template.send(TEMPLATE_TOPIC, 0, 2, "baz");
        received = records.poll(10, TimeUnit.SECONDS);
        assertThat(received, hasKey(2));
        assertThat(received, hasPartition(0));
        assertThat(received, hasValue("baz"));
    }

    /**
     * spring??????container
     * ??????container????????????listener
     * container = containerProperties + consumerFactory + listener
     *
     * KafkaMessageListenerContainer????????????ListenerConsumer??????????????????ListenerConsumer??????{@link org.springframework.kafka.listener.KafkaMessageListenerContainer.ListenerConsumer}
     *
     * ??????KafkaMessageListenerContainer??????{@link org.springframework.context.SmartLifecycle}???????????????????????????
     * ??????????????????{@link org.springframework.kafka.config.KafkaListenerEndpointRegistry}?????????
     *
     * ConcurrentMessageListenerContainer???????????????KafkaMessageListenerContainer {@link org.springframework.kafka.listener.ConcurrentMessageListenerContainer}
     *
     * @return
     */
    private BlockingQueue<ConsumerRecord<Integer, String>> container() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testT", "false",
            EmbeddedKafkaHolder.getEmbeddedKafka());
        DefaultKafkaConsumerFactory<Integer, String> cf =
                            new DefaultKafkaConsumerFactory<Integer, String>(consumerProps);
        ContainerProperties containerProperties = new ContainerProperties(TEMPLATE_TOPIC);
        KafkaMessageListenerContainer<Integer, String> container =
                            new KafkaMessageListenerContainer<>(cf, containerProperties);
        final BlockingQueue<ConsumerRecord<Integer, String>> records = new LinkedBlockingQueue<>();
        container.setupMessageListener(new MessageListener<Integer, String>() {

            @Override
            public void onMessage(ConsumerRecord<Integer, String> record) {
                System.out.println(record);
                records.add(record);
            }

        });
        container.setBeanName("templateTests");
        container.start();
        ContainerTestUtils.waitForAssignment(container,
                            EmbeddedKafkaHolder.getEmbeddedKafka().getPartitionsPerTopic());
        return records;
    }


    @Test
    public void consumer(){
        Map<String, Object> consumerProps = KafkaTestUtils
            .consumerProps("testGroup", "false", EmbeddedKafkaHolder.getEmbeddedKafka());
        //consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        // ??????????????????
        //consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        // ??????????????????????????????
        //consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        // autocommit?????????????????????autocommit???????????????????????????offset??????????????????????????????????????????offset
        consumerProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10");
        // ???????????????fetch???????????????
        consumerProps.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
        // fetch ??????????????????????????????????????????????????????fetch.min.bytes?????????????????????fetch.max.wait??????
        consumerProps.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);
        // ??????consumer???????????????????????????????????????
        consumerProps.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,
            ConsumerConfig.DEFAULT_MAX_PARTITION_FETCH_BYTES
        );
        // ??????consumer.poll?????????????????????records??????
        consumerProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        // ???????????????session????????????????????????????????????consumer???????????????????????????????????????
        consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000");
        // ????????????????????????????????????sessionTimeout????????????
        consumerProps.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        // ??????auto.offset.reset???????????????????????????("latest", "earliest"),latest????????????????????????offset???????????????earliest??????????????????offset??????
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        ConsumerFactory<Integer,String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);

        Consumer<Integer, String> consumer = cf.createConsumer();

        consumer.subscribe(Collections.singletonList("templateTopic"));
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(100));
            System.out.println(records.count());
        }

    }

}