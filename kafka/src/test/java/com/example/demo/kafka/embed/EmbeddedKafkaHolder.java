
package com.example.demo.kafka.embed;

import kafka.common.KafkaException;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;

public final class EmbeddedKafkaHolder {

    private static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, false);

    private static boolean started;

    public static EmbeddedKafkaBroker getEmbeddedKafka() {
        if (!started) {
            try {
                embeddedKafka.before();
            }
            catch (Exception e) {
                throw new KafkaException(e);
            }
            started = true;
        }
        return embeddedKafka.getEmbeddedKafka();
    }

    private EmbeddedKafkaHolder() {
        super();
    }

}