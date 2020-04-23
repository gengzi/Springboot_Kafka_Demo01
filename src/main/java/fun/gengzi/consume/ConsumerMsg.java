package fun.gengzi.consume;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collections;

/**
 *  消费
 */
public class ConsumerMsg {
    /**
     * 消费消息
     */
    private void consumerMsg() {
        Consumer<String, String> consumer = ConsumerCreator.createConsumer();
        while (true) {
            // 订阅主题  ,可以传递 一个 list
            consumer.subscribe(Collections.singletonList("TEST_TOPIC"));
            // 超时设定，这poll 之后， kafka 就认为已经成功消费了消息
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> consumerRecord :
                    poll) {
                String value = consumerRecord.value();
                System.err.println("Consumer consume Msg" + value);
                break;

            }
        }
    }


}
