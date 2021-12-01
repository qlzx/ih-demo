package com.example.demo.kafka.base;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Utils;
import org.junit.Test;

/**
 * @author lh0
 * @date 2021/8/18
 * @desc
 */
public class PartitionTest {

    // topic所拥有的partition总数
    private static final int PARTITION_NUMS = 20;

    /**
     * 1. 指定了Key，通过以下算法获取partition
     * 2. 没有指定Key，使用轮询
     * @see org.apache.kafka.clients.producer.internals.DefaultPartitioner#partition
     */
    @Test
    public void cal_partitionForKey() {
        StringSerializer serializer = new StringSerializer();
        byte[] bytes = serializer.serialize("kafka.scanAllNew", "85585001275743");
        int partition = toPositive(Utils.murmur2(bytes)) % PARTITION_NUMS;
        System.out.println(partition);
    }

    private int toPositive(int number) {
        return number & 0x7fffffff;
    }
}
