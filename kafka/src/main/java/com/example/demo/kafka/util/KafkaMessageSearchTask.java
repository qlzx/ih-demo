package com.example.demo.kafka.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Utils;

/**
 * @author lh0
 * @date 2021/8/23
 * @desc
 */
public class KafkaMessageSearchTask {

    // 1. 根据时间戳获取对应的消息

    //public void getMessage(long timestamp,String topic){
    //    Consumer consumer = new KafkaConsumer<String, String>();
    //
    //    consumer.
    //}

    // 2.

    private final KafkaConsumer<String,KafkaMessage> consumer;

    public KafkaMessageSearchTask(
        KafkaConsumer<String, KafkaMessage> consumer) {
        this.consumer = consumer;
    }

    /**
     * 查询kafka消息
     * @param topic
     * @param key
     * @param beginTime
     * @param endTime
     */
    public void searchMessage(String topic, String key, Long beginTime, Long endTime) {
        List<TopicPartition> topicPartitions = getTopicPartitions(topic,key);
        consumer.assign(topicPartitions);

        Map<TopicPartition, Long> map = topicPartitions.stream()
            .collect(Collectors.toMap(v -> v, v -> beginTime));

        Map<TopicPartition, OffsetAndTimestamp> offsetAndTimestampMap = consumer
            .offsetsForTimes(map);
    }

    private List<TopicPartition> getTopicPartitions(String topic, String key) {
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        if (key != null) {
            StringSerializer stringSerializer = new StringSerializer();

            int partition = Utils.toPositive(Utils.murmur2(stringSerializer.serialize(topic, key)))
                % partitionInfos.size();
            return Collections.singletonList(new TopicPartition(topic, partition));
        }else {
            List<TopicPartition> topicPartitions = new ArrayList<>(partitionInfos.size());
            partitionInfos.forEach(item -> topicPartitions.add(new TopicPartition(topic, item.partition())));
            return topicPartitions;
        }
    }




    class ProtostuffDeserializer implements Deserializer<KafkaMessage> {

        public static final String ERROR_DOCTYPE = "MALFORMED_KAFKA_MESSAGE";

        @Override
        public void configure(Map configs, boolean isKey) {

        }

        @Override
        /**
         * 如果KafkaMessage中的messages列表的类型无法解析，会产生ClassNotFoundExcption异常，引起Consumer的死循环
         * */
        public KafkaMessage deserialize(String topic, byte[] data) {
            KafkaMessage message = new KafkaMessage();
            try {
                ProtostuffIOUtil.mergeFrom(data, message, RuntimeSchema.getSchema(KafkaMessage.class));
            } catch (Exception e) {
                if (e.getCause() instanceof ClassNotFoundException) {
                    e.printStackTrace();
                }else {
                    e.printStackTrace();
                }
                message.setDocType(ERROR_DOCTYPE);
            }
            return message;
        }

        @Override
        public void close() {

        }
    }

}