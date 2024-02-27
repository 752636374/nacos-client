package com.alibaba.nacos.example.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-22  20:09
 * @Description: 消费者
 * @Version: 1.0
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqGroup.transaction, topic = MqTopic.transaction)
public class SimpleTransactionMessageConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("收到消息: msgId={} topic={} queueId={} body={}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getQueueId(), body);
        // 业务执行完毕没有抛出异常则由代理类自动提交ACK
    }
}
