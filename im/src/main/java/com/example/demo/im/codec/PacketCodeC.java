package com.example.demo.im.codec;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.im.Command;
import com.example.demo.im.pack.BasePacket;
import com.example.demo.im.pack.LoginRequestPacket;
import com.example.demo.im.pack.LoginResponsePacket;
import com.example.demo.im.pack.MessageRequestPacket;
import com.example.demo.im.pack.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author lh0
 * @date 2021/6/20
 * @desc
 */
public class PacketCodeC {
    private PacketCodeC() {}

    public static PacketCodeC getInstance() {
        return PacketCodecHolder.bean;
    }

    public static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encode(ByteBuf byteBuf,BasePacket packet) {
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator,BasePacket packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public BasePacket decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends BasePacket> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return SERIALIZER_MAP.get(serializeAlgorithm);
    }

    private Class<? extends BasePacket> getRequestType(byte command) {
        return COMMAND_PACKETS.get(command);
    }

    private static final Map<Byte, Class<? extends BasePacket>> COMMAND_PACKETS = new HashMap<>();

    private static final Map<Byte, Serializer> SERIALIZER_MAP = new HashMap<>();

    static {
        COMMAND_PACKETS.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        COMMAND_PACKETS.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        COMMAND_PACKETS.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        COMMAND_PACKETS.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);


        SERIALIZER_MAP.put(SerializerAlgorithm.JSON, Serializer.DEFAULT);
    }

    static class PacketCodecHolder {
        public static PacketCodeC bean = new PacketCodeC();
    }
}
