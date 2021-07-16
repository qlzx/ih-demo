package com.example.demo.kafka.configure;

import java.util.Map;

import com.example.demo.kafka.listener.CustomerKafkaListener;
import com.example.demo.kafka.listener.handler.LogErrorHandler;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

/**
 * @author lh0
 * @date 2021/7/16
 * @desc
 */
@Configuration
public class EmbeddedKafkaConfig {

    @Bean
    public KafkaAdmin admin(KafkaProperties properties){
        KafkaAdmin admin = new KafkaAdmin(properties.buildAdminProperties());
        // 默认这个值是False的，在Broker不可用时，不影响Spring 上下文的初始化。如果你觉得Broker不可用影响正常业务需要显示的将这个值设置为True
        admin.setFatalIfBrokerNotAvailable(true);
        return admin;
    }




    @Bean
    public CustomerKafkaListener customerKafkaListener(){
        return new CustomerKafkaListener();
    }

    @Bean
    public LogErrorHandler logErrorHandler(){
        return new LogErrorHandler();
    }

    @Bean
    public ProducerFactory<Object,Object> producerFactory(EmbeddedKafkaBroker embeddedKafka){
        return new DefaultKafkaProducerFactory<>(
            KafkaTestUtils.producerProps(embeddedKafka));

    }

    @Bean
    public KafkaTemplate<?, ?> kafkaTemplate(
        @Qualifier("producerFactory") ProducerFactory<Object, Object> producerFactory,
        ProducerListener<Object, Object> kafkaProducerListener) {

        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerListener(kafkaProducerListener);
        kafkaTemplate.setDefaultTopic("templateTopic");
        return kafkaTemplate;
    }

    @Bean
    public ProducerFactory<Object,Object> txProducerFactory(EmbeddedKafkaBroker embeddedKafka){
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafka);
        producerProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        DefaultKafkaProducerFactory<Object, Object> producerFactory = new DefaultKafkaProducerFactory<>(
            producerProps);
        producerFactory.setTransactionIdPrefix("kafka_tx.");
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<?, ?> kafkaTxTemplate(
        @Qualifier("txProducerFactory") ProducerFactory<Object, Object> producerFactory,
        ProducerListener<Object, Object> kafkaProducerListener) {

        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerListener(kafkaProducerListener);
        kafkaTemplate.setDefaultTopic("templateTopic");
        return kafkaTemplate;
    }


    @Bean
    public ConsumerFactory<?,?> consumerFactory(EmbeddedKafkaBroker embeddedKafka){
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testT", "false",
            embeddedKafka);


        return new DefaultKafkaConsumerFactory<>(consumerProps);
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory(
        KafkaTemplate<Object,Object> kafkaTemplate,
        ConsumerFactory<Object,Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.getContainerProperties().setGroupId("test-group-1");
        //当使用手动提交时必须设置ackMode为MANUAL,否则会报错No Acknowledgment available as an argument, the listener container must have a MANUAL AckMode to populate the Acknowledgment.
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.getContainerProperties().setAckCount(10);
        factory.getContainerProperties().setAckTime(10000);
        // 设置转发template
        factory.setReplyTemplate(kafkaTemplate);
        // 最大重试三次 (重试机制即当前consumer线程一直不提交offset，每次poll都会poll重复的数据，
        // 直到超过{@link org.springframework.kafka.listener.FailedRecordTracker.failures})中最大失败数
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate), 3));




        return factory;
    }


    @Bean
    public NewTopic topic2() {
        return new NewTopic("topic-kl", 1, (short) 1);
    }



}
