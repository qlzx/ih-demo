package com.example.demo.im.codec;

import com.alibaba.fastjson.JSON;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class FastJsonSerializer implements Serializer{
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
