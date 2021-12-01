package com.example.demo.kafka.consumer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class KafkaMessage implements Serializable {

    private String docType;

    private List<?> messages = new ArrayList<>();

    private String globalID;

    private Integer resendCount = 0;

    private String groupId;

    /**
     * 附件
     */
    private Map<String, String> attachment = new HashMap<String, String>();

    private String proenv;

    private String linkFlag;

    public KafkaMessage() {

    }

    public KafkaMessage(String docType, List<?> messages) {
        this.docType = docType;
        this.messages = messages;
    }
}
