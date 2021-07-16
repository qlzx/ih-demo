package com.example.demo.im.codec;

import com.alibaba.fastjson.serializer.JSONSerializer;

/**
 * @author lihao
 */
public interface Serializer {

    Serializer DEFAULT = new FastJsonSerializer();

    /**
     * 序列化算法
     * @return 获取序列化方式
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     * @param object java对象
     * @return 二进制数组
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     * @param bytes 二进制
     * @param clazz Java类型
     * @return java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
