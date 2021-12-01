package com.example.demo.kafka;

import java.util.Map;

import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

public class ProtostuffDeserializer implements Deserializer<KafkaMessage> {

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
                    //e.printStackTrace();
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
