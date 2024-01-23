import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class producerTest {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer mqProducer = new DefaultMQProducer("broker-a");
        mqProducer.setNamesrvAddr("127.0.0.1:9876");
        mqProducer.start();
        Message msg = new Message("RLT_TEST_TOPIC",
                "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult sendResult = mqProducer.send(msg);
        System.out.printf("%s%n", sendResult);
        mqProducer.shutdown();
        while(true){
        }
    }
}
