package fun.gengzi.test;

import fun.gengzi.consume.ConsumerCreator;
import fun.gengzi.productor.ProducerCreator;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class MyTest {

    // 定义 topic
    private static final String TEST_TOPIC = "testTopic";

    /**
     * 生产消息
     */
    private void sendMsg() {
        Producer<String, String> producer = ProducerCreator.createProducer();
        // 创建消息
        ProducerRecord producerRecord = new ProducerRecord<String, String>(TEST_TOPIC, "HELLO KAFKA");
        // 发送消息
        try {
            RecordMetadata recordMetadata = (RecordMetadata) producer.send(producerRecord).get();
            System.out.println("Record send to partition" + recordMetadata.partition() + " with offset" + recordMetadata.offset());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    /**
     * 消费消息
     */
    private void consumerMsg() {
        Consumer<String, String> consumer = ConsumerCreator.createConsumer();
        while (true) {
            // 订阅主题
            consumer.subscribe(Collections.singletonList(TEST_TOPIC));

            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> consumerRecord :
                    poll) {
                String value = consumerRecord.value();
                System.err.println("Consumer consume Msg" + value);
                break;

            }
        }
    }


    public static void main(String[] args) {
        MyTest myTest = new MyTest();
        myTest.sendMsg();
        myTest.consumerMsg();
    }


}
