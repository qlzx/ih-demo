package com.example.demo.kafka;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * @author lh0
 * @date 2021/8/23
 * @desc
 */
public class Client {

    public Client(String clientName) {
        this.clientName = clientName;
        this.consumer = new SimpleConsumer("10.13.37.50", 9092, 100000, 64 * 1024, clientName);
    }

    public static void main(String[] args) {
        StringDeserializer keyDeserializer = new StringDeserializer();
        ProtostuffDeserializer valueDeserializer = new ProtostuffDeserializer();

        Properties consumerProp = new Properties();
        consumerProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.13.37.50:9092");
        consumerProp.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10000);
        consumerProp.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 10 * 1024 * 1024);
        consumerProp.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 20 * 1024 * 1024);
        consumerProp.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 3000);

        Client localhost = new Client("kafkaClient");

        long[] offsets = localhost.getOffsets("kafka.scanAllNew", 0,
            LocalDateTime.of(LocalDate.of(2021,8,23), LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli());


        long[] endOffsets = localhost.getOffsets("kafka.scanAllNew", 0,
            LocalDateTime.of(LocalDate.of(2021, 8, 23), LocalTime.MAX).toInstant(ZoneOffset.of("+8")).toEpochMilli());

        System.out.println("get offset:" + offsets[0]);

        System.out.println("end offset:" + endOffsets[0]);


        try (KafkaConsumer<String, KafkaMessage> kafkaConsumer = new KafkaConsumer<>(consumerProp,
            keyDeserializer, valueDeserializer)) {

            TopicPartition topicPartition = new TopicPartition("kafka.scanAllNew", 0);

            kafkaConsumer.assign(Collections.singletonList(topicPartition));
            //kafkaConsumer.seekToBeginning(topicPartition, offsets[0]);

            // 计算offset range进行分配consumer 100000

            while (isRunning()) {
                ConsumerRecords<String, KafkaMessage> records = kafkaConsumer.poll(3000);
                if (!records.isEmpty()) {
                    System.out.println("===============poll size:" + records.records(topicPartition).size());

                    for (ConsumerRecord<String, KafkaMessage> item : records
                        .records(topicPartition)) {
                        if (item.offset() > endOffsets[0]) {
                            return ;
                        }

                        System.out.println("offset:" + item.offset());
                        System.out.println("value:" + item.value());
                        System.out.println("key" + item.key());
                    }

                }else {
                    System.out.println("record poll empty");
                }

            }

            //LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            //long beginTime = now.toEpochSecond(ZoneOffset.of("+8"));
            //
            //Map<TopicPartition, OffsetAndTimestamp> offsetAndTimestampMap = kafkaConsumer
            //    .offsetsForTimes(ImmutableBiMap.of(topicPartition, beginTime));

            //System.out.println(offsetAndTimestampMap);


            //OffsetFetchRequest fetchRequest = new OffsetFetchRequest(null, partitions, (short) 0, correlationId, clientId);
            //List<TopicPartition> list=new ArrayList<>();
            //for (Entry<Integer, PartitionMetadata> entry : metadatas.entrySet()) {
            //    int partition = entry.getKey();
            //    try {
            //        channel.send(fetchRequest.underlying());
            //        OffsetFetchResponse fetchResponse = OffsetFetchResponse.readFrom(channel.receive().payload());
            //
            //        TopicAndPartition testPartition0 = new TopicAndPartition(topic, partition);
            //        Map<TopicAndPartition, OffsetMetadataAndError> offsets = fetchResponse.offsets();
            //        OffsetMetadataAndError result = offsets.get(testPartition0);
            //        OffsetAndMetadata committed = consumer.committed(new TopicPartition(topic, partition));
            //        long partitionOffset = committed.offset();
            //        System.out.println("获取提交的偏移量--------" + topic + "_" + partition + ":" + partitionOffset);
            //        sumOffset += partitionOffset;
            //        String leadBroker = entry.getValue().leader().host();
            //        String clientName = "Client_" + topic + "_" + partition;
            //        SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 100000, 64 * 1024, clientName);
            //        long readOffset = getLastOffset(consumer, topic, partition, kafka.api.OffsetRequest.LatestTime(),
            //            clientName);
            //        // System.out.println("最近时间："+kafka.api.OffsetRequest.LatestTime());
            //        sum += readOffset;
            //        if (consumer != null)
            //            consumer.close();
            //    } catch (Exception e) {
            //        channel.disconnect();
            //    }
            //}
        } finally {

        }
    }

    private static boolean running = true;

    private static boolean isRunning() {
        return running;
    }

    public long[] getOffsets (String topic,int partition, long time) throws RuntimeException {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(time, 1));
        kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), this.clientName);
        OffsetResponse response = consumer.getOffsetsBefore(request);

        if (response.hasError()) {
            throw new RuntimeException("Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partition));
        }
        return response.offsets(topic, partition);
    }

    private final String clientName;

    private final SimpleConsumer consumer;


}

