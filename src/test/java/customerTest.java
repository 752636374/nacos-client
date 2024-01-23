
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class customerTest {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broker-a");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("RLT_TEST_TOPIC", "*");//订阅全部
        //consumer.subscribe("TopicTest1", "TagA || TagB");订阅多个
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    if (msg.getTopic().equals("TopicTest1")) {
                        if (msg.getTags() != null && msg.getTags().equals("TagA")) {
                            System.out.println("TagA:" + new String(msg.getBody()));
                        }
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
