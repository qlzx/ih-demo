package com.example.demo.kafka.client;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author lh0
 * @date 2021/7/16
 * @desc
 */

public class Producer {

    public static final int SWITCH = 2;

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094");
        // 设置生产者幂等
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_DOC, true);
        // 生产者重试
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 300);
        //properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION)
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "test");
        // 批次最大消息可以使用的内存大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 批次最大1毫秒的等待时间
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // 生产者消息缓冲区最大内存
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 32 * 1024 * 1024L);
        // 当缓冲区满了或者没有可用元数据就会阻塞生产者，最大阻塞等待时间
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 60000);
        // 生产者请求的消息体最大1M，和broker配置的max.message.size一致，超过则被被拒绝
        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 1024 * 1024);

        properties
            .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties
            .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<>(properties);
        int random = new Random().nextInt(10);
        Integer key = 1;
        while (true) {
            send(kafkaProducer, key, random % SWITCH == 0);
            key++;
        }

    }

    private static void send(KafkaProducer<Integer, String> kafkaProducer, Integer key, boolean isAsync) {
        ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>("test",
            key, "hello" + key);
        if (isAsync) {
            kafkaProducer.send(producerRecord, new DemoCallBack(key));
        } else {
            try {
                kafkaProducer.send(producerRecord).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    static class DemoCallBack implements Callback {
        private final Integer key;

        DemoCallBack(Integer key) {this.key = key;}

        @Override
        public void onCompletion(RecordMetadata metadata, Exception exception) {
            if (exception != null) {
                System.out.println("producer key:" + key + " fail " + exception.getMessage());
            } else {
                System.out.println("producer key" + key + " success " + metadata.toString());
            }
        }
    }

}
