package com.example.demo.kafka.consumer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableBiMap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author lh0
 * @date 2021/8/23
 * @desc
 */
public class KafkaConsumerTest {
    private StringDeserializer stringDeserializer = new StringDeserializer();

    private StringDeserializer valueDeserializer = new StringDeserializer();


    @Test
    public void testInstance(){
        Map<String, Object> consumerProp = new HashMap<>();
        consumerProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.13.72.8:9092");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProp,
            stringDeserializer, valueDeserializer);

        Assert.assertNotNull(kafkaConsumer);
    }

    @Test
    public void testListTopics(){
        Map<String, Object> consumerProp = new HashMap<>();
        consumerProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.13.72.8:9092");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProp,
            stringDeserializer, valueDeserializer);

        Map<String, List<PartitionInfo>> stringListMap = kafkaConsumer.listTopics();

        stringListMap.forEach((k,v)->{
            System.out.println(k);
        });


    }

    @Test
    public void testAssignment(){
        Map<String, Object> consumerProp = new HashMap<>();
        consumerProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.13.72.8:9092");
        consumerProp.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProp,
            stringDeserializer, valueDeserializer);

        Set<TopicPartition> assignment = kafkaConsumer.assignment();
        System.out.println(assignment);

        TopicPartition topicPartition = new TopicPartition("kafka.scanAllNew", 0);

        kafkaConsumer.assign(Collections.singletonList(topicPartition));


        assignment = kafkaConsumer.assignment();
        System.out.println("after assign:" + assignment);

        kafkaConsumer.seekToBeginning(Collections.singletonList(topicPartition));

        long position = kafkaConsumer.position(topicPartition);
        System.out.println("offset:" + position);

        //kafkaConsumer.seek();

        kafkaConsumer.close();
    }

    @Test
    public void testSubscription(){
        Map<String, Object> consumerProp = new HashMap<>();
        consumerProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.13.72.8:9092");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProp,
            stringDeserializer, valueDeserializer);

        Set<String> subscription = kafkaConsumer.subscription();
        System.out.println(subscription);

        kafkaConsumer.subscribe(Collections.singletonList("kafka.scanAllNew"));

        subscription = kafkaConsumer.subscription();
        System.out.println("after subscription:" + subscription);

        kafkaConsumer.close();

        CharSequence text;
        Duration duration = Duration.parse("");
        ConsumerRecords<String, String> poll = kafkaConsumer.poll(duration);

        // 1.获取到对应的partition
        // 2.根据时间和TopicPartition获取到对应的offset
        // 3.根据offset，调用consumer.poll()来读取消息
        // 4. 过滤出需要的消息 直到offset大于[offsetRange]

    }

    @Test
    public void testSearch(){
        Map<String, Object> consumerProp = new HashMap<>();
        consumerProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.13.72.8:9092");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProp,
            stringDeserializer, valueDeserializer);

        TopicPartition topicPartition = new TopicPartition("kafka.scanAllNew", 0);

        kafkaConsumer.assign(Collections.singletonList(topicPartition));

        LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        long beginTime = now.toEpochSecond(ZoneOffset.of("+8"));

        Map<TopicPartition, OffsetAndTimestamp> offsetAndTimestampMap = kafkaConsumer
            .offsetsForTimes(ImmutableBiMap.of(topicPartition, beginTime));

        //System.out.println(offsetAndTimestampMap);


    }






}
