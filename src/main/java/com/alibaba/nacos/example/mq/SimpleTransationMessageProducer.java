package com.alibaba.nacos.example.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


/***
 * @Description: 生产者
 * @Author: yaoheng5
 * @date 2024/2/22 20:15
 * @return
 */
@Slf4j
@Component
public class SimpleTransationMessageProducer {
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    /**
     * 发送事务消息
     */
    public void send(String msg) {
        // 发送事务消息:采用的是sendMessageInTransaction方法，返回结果为TransactionSendResult对象，该对象中包含了事务发送的状态、本地事务执行的状态等
        // 第三个参数作为业务参数传递，后续业务场景分析-以及代码实现中会重点使用到这个参数
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(MqGroup.transaction,
                MqTopic.transaction, MessageBuilder.withPayload(msg).build(), null);
        String transactionId = result.getTransactionId();
        String status = result.getSendStatus().name();
        log.info("发送消息成功 transactionId={} status={} ", transactionId, status);
    }
}
