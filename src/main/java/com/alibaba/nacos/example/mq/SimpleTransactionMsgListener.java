package com.alibaba.nacos.example.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;

/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-22  20:04
 * @Description: 生产者事务监听器
 * @Version: 1.0
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = MqGroup.transaction)
public class SimpleTransactionMsgListener implements RocketMQLocalTransactionListener {
    // 事务消息共有三种状态，提交状态、回滚状态、中间状态：
    // RocketMQLocalTransactionState.COMMIT: 提交事务，它允许消费者消费此消息。
    // RocketMQLocalTransactionState.ROLLBACK: 回滚事务，它代表该消息将被删除，不允许被消费。
    // RocketMQLocalTransactionState.UNKNOWN: 中间状态，它代表需要检查消息队列来确定状态。
    /** 执行本地事务（在发送消息成功时执行）  */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("监听到消息：{}",message.getPayload().toString());
        //TODO 开启本地事务（实际就是我们的jdbc操作）
        //TODO 执行业务代码（例如插入订单数据库表等）
        //TODO 提交或回滚本地事务
        //模拟一个处理结果
        int index=1;
        /**
         * 模拟返回事务状态
         */
        switch (index){
            case 1:
                //处理业务
                String jsonStr = new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
                log.info("本地事务回滚，回滚消息，"+jsonStr);
                //返回ROLLBACK状态的消息会被丢弃
                return RocketMQLocalTransactionState.ROLLBACK;
            case 2:
                //返回UNKNOW状态的消息会等待Broker进行事务状态回查
                log.info("需要等待Broker进行事务状态回查");
                return RocketMQLocalTransactionState.UNKNOWN;
            default:
                log.info("事务提交，消息正常处理");
                //返回COMMIT状态的消息会立即被消费者消费到
                return RocketMQLocalTransactionState.COMMIT;
        }
    }
    /**
     * 检查本地事务的状态
     * 回查间隔时间：系统默认每隔60秒发起一次定时任务，对未提交的半事务消息进行回查，共持续12小时。
     * 第一次消息回查最快
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String transactionId = message.getHeaders().get("__transactionId__").toString();
        log.info("检查本地事务状态，transactionId：{}", transactionId);
        return RocketMQLocalTransactionState.COMMIT;
    }
}
