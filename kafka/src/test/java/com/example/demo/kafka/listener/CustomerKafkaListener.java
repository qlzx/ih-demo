package com.example.demo.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * @author lh0
 * @date 2021/7/16
 * @desc
 */
@Slf4j
public class CustomerKafkaListener {

    /**
     * {@link KafkaListener}
     * 显示的指定消费哪些Topic和分区的消息，
     * 设置每个Topic以及分区初始化的偏移量，
     * 设置消费线程并发度
     * 设置消息异常处理器
     *
     * @param input
     * @return
     */
    @KafkaListener(id = "webGroup", topicPartitions = {
        @TopicPartition(topic = "topic1", partitions = {"0", "1"}),
        @TopicPartition(topic = "topic2", partitions = "0",
            partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
    }, concurrency = "6", errorHandler = "logErrorHandler")
    public String listen(String input) {
        log.info("input value: {}", input);
        return "successful";
    }

    /**
     * 设置手动ACK
     *
     * @return
     */
    @KafkaListener(id = "manual", topics = "templateTopic", concurrency = "1", errorHandler = "logErrorHandler")
    @SendTo("templateTopicTo")
    public String manualAck(String input, Acknowledgment acknowledgment) {
        log.info("input value: {}", input);
        if ("ack".equals(input)) {
            acknowledgment.acknowledge();
        }
        return "success";
    }

    /**
     * 设置手动ACK
     *
     * @return
     */
    @KafkaListener(id = "manualT", topics = "templateTopicTo", concurrency = "1", errorHandler = "logErrorHandler")
    public void sendTo(String input) {
        log.info("input value: {}", input);
    }

    /**
     * 设置autoStartup为false，可以通过{@link org.springframework.kafka.config.KafkaListenerEndpointRegistry}来启动和关闭
     *
     * @param input
     * @return
     */
    @KafkaListener(id = "auto", topics = "templateTopic", concurrency = "1", errorHandler = "logErrorHandler", autoStartup = "false")
    public String autoStartup(String input) {
        log.info("input value: {}", input);
        return "success";
    }
}
