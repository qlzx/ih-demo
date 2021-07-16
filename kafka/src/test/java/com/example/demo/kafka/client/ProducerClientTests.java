package com.example.demo.kafka.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.example.demo.kafka.KafkaTestApplication;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
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
public class ProducerClientTests {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<Integer,String> template;


    @Autowired
    @Qualifier("kafkaTxTemplate")
    private KafkaTemplate<Integer,String> kafkaTxTemplate;


    @Autowired
    private KafkaProperties kafkaProperties;


    @Test
    public void contextLoads() throws IOException {
        System.out.println(embeddedKafka);
        System.out.println(embeddedKafka.getBrokersAsString());
        System.out.println(template);
        System.out.println(kafkaTxTemplate);
    }

    @Test
    public void createTopic(){
        AdminClient client = AdminClient.create(kafkaProperties.buildAdminProperties());
        if(client !=null){
            try {
                Collection<NewTopic> newTopics = new ArrayList<>(1);
                newTopics.add(new NewTopic("topic-kl",1,(short) 1));
                client.createTopics(newTopics);
            }catch (Throwable e){
                e.printStackTrace();
            }finally {
                client.close();
            }
        }
    }

    /**
     * 1、事务日志副本集大于Broker数量，会抛如下异常：
     *
     * Number of alive brokers '1' does not meet the required replication factor '3'
     * for the transactions state topic (configured via 'transaction.state.log.replication.factor').
     * This error can be ignored if the cluster is starting up and not all brokers are up yet.
     * 默认Broker的配置transaction.state.log.replication.factor=3，单节点只能调整为1
     *
     * 2、副本数小于副本同步队列数目，会抛如下异常
     *
     * Number of insync replicas for partition __transaction_state-13 is [1], below required minimum [2]
     * 默认Broker的配置transaction.state.log.min.isr=2，单节点只能调整为1
     */
    @Test
    public void testTxMessage(){
        // 需要配置开启事务消息 spring.kafka.producer.transaction-id-prefix=kafka_tx.
        // 开启事务消息后默认开启 生产者幂等， 生产者幂等需要配置重试次数>1
        kafkaTxTemplate.executeInTransaction(t->{
            t.send("templateTopic", "var1");
            t.send("templateTopic", "var2");
            return true;
        });
    }

    /**
     * 如果生产者配置了事务后，不在Transaction里面使用，或者不发送事务消息，则会抛出如下异常
     * <p>
     * java.lang.IllegalStateException: No transaction is in process;
     * possible solutions:
     * run the template operation within the scope of a template.executeInTransaction() operation,
     * start a transaction with @Transactional before invoking the template method,
     * run in a transaction started by a listener container when consuming a record
     */
    @Test(expected = IllegalStateException.class)
    public void testTxSend() {
        kafkaTxTemplate.send("templateTopic", "var1");
    }

    /**
     * 普通无事务的发送方式
     */
    @Test
    public void tetSend() {
        template.send("templateTopic", "var1");
    }


    @Test
    public void testKafkaAddTopic() {
        embeddedKafka
            .addTopics(new NewTopic("TEMPLATE_TOPIC", 10, (short) 1), new NewTopic("TEMPLATE_TOPIC_2", 15, (short) 1));
    }

    @Test
    public void consumer(){



    }
}
