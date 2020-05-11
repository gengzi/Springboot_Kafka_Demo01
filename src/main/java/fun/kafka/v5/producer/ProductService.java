package fun.kafka.v5.producer;

import fun.kafka.v5.config.ProducerConfigPro;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.RetriableException;

public class ProductService {

    public void sendMessage(String topic, Object o) {
        Producer<String, String> producer = ProducerConfigPro.createProducer();
        // 初始化事务
        producer.initTransactions();
        // 开启事务
        producer.beginTransaction();
        // 发送消息
        try {
            for (int i = 0; i < 100; i++) {
                String Msg = (String) o + ":" + i;
                ProducerRecord producerRecord = new ProducerRecord<String, String>(topic, (String) o);
                producer.send(producerRecord);
//                if(i==50){
//                    throw new Exception("出错了");
//                }
            }

            // 提交事务
            producer.commitTransaction();

        } catch (Exception e) {
            e.printStackTrace();
            producer.abortTransaction();
        }

    }
}
