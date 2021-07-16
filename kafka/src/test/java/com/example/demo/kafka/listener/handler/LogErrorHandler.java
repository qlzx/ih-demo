package com.example.demo.kafka.listener.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;

/**
 * 消息异常处理
 * @author lh0
 * @date 2021/7/16
 * @desc
 */
@Slf4j
public class LogErrorHandler implements KafkaListenerErrorHandler {

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        log.error("consumer fail message:{} cause by:{}", message.toString(),
            exception.getCause().getMessage());
        // 这里可以进行补偿重试， 持久化日志 persistDB(); retry(message);
        return null;
    }

}
