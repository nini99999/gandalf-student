package com.poshist.common.queue;


import com.poshist.common.webSocket.CustomWebSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AlertConsumer {
    private static final Logger log = LogManager.getLogger();
    @RabbitHandler
    @RabbitListener(queues = TTLQueueConfig.ALERT_QUEUE,concurrency="4-10")
    public void consumeTimeOutQueue(@Payload String data) throws IOException {
        CustomWebSocket.sendInfo(data);
        log.info("定时消息--"+data);
    }
}
