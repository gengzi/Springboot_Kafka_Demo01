package fun.kafka.v5.test;


import fun.kafka.v5.config.ConsumerConfigPro;
import fun.kafka.v5.config.ProducerConfigPro;
import fun.kafka.v5.consumer.ConsumerService;
import fun.kafka.v5.producer.ProductService;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.protocol.types.Field;
import org.assertj.core.util.Maps;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MsgExec {


    public void sendMsg() {
        ProductService productService = new ProductService();
        productService.sendMessage("v1-topic3", "hello");
    }

    public void consumMsg() {
        ConsumerService consumerService = new ConsumerService();
        consumerService.consumerMsg();
    }
    //测试

    public static void main(String[] args) {
        MsgExec msgExec = new MsgExec();
        msgExec.sendMsg();
     //   msgExec.consumMsg();
        msgExec.consumerTransfromProduce();

    }


    public void consumerTransfromProduce() {
        // 创建消费者
        ConsumerConfigPro consumerConfigPro = new ConsumerConfigPro();
        KafkaConsumer kafkaConsumer = new KafkaConsumer<String, String>(consumerConfigPro.getConsumerConfig());
        // 订阅topic
        kafkaConsumer.subscribe(Arrays.asList("v1-topic3"));
        // 创建生产者
        Producer<String, String> producer = ProducerConfigPro.createProducer();
        // 初始化事务
        producer.initTransactions();

        // 消费数据
        while (true) {
            // 开启事务
            producer.beginTransaction();
            // 消费数据
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
            // 封装
            Map<TopicPartition, OffsetAndMetadata> commits = new HashMap<>(128);
            try {
                records.forEach((record) -> {
                    System.out.printf("打印："+"offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                    // 记录提交的偏移量
                    commits.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()));
                    // 生产数据
                    String value = record.value();
                    // 发送新的消息
                    producer.send(new ProducerRecord<String, String>("v2-topic3", value));
                });
                // 提交偏移量
                producer.sendOffsetsToTransaction(commits, "v1-consumer-group");
                // 事务提交
                producer.commitTransaction();

            } catch (Exception e) {
                e.printStackTrace();
                producer.abortTransaction();
            }

        }
    }
}
