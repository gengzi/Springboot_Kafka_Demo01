package fun.kafka.v1.consumer;

import fun.kafka.v1.config.ConsumerConfigPro;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.internals.NoOpConsumerRebalanceListener;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 *  消费者服务
 */
public class ConsumerService {

    public void consumerMsg(){
        // 配置消费者
        ConsumerConfigPro consumerConfigPro = new ConsumerConfigPro();
        Properties consumerConfig = consumerConfigPro.getConsumerConfig();
        // 根据配置项，创建消费者
        KafkaConsumer kafkaConsumer = new KafkaConsumer<String,String>(consumerConfig);
        // 订阅topic
        kafkaConsumer.subscribe(Arrays.asList("v1-topic3","v2-topic2"));
        // 使用正则匹配所有的 topic  ConsumerRebalanceListener 用于 rebalance 用于平衡算法  NoOpConsumerRebalanceListener 表示什么都不做
        // kafkaConsumer.subscribe(Pattern.compile("kafka.*"),new NoOpConsumerRebalanceListener());
        // 消费数据
        try {
            while (true) {
                // poll 获取消息，1000 是超时设定，控制阻塞的最大时间
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
                records.forEach((record) -> {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                });
            }
        }finally {
            if(kafkaConsumer != null){
                kafkaConsumer.close();
            }
        }



    }



}
