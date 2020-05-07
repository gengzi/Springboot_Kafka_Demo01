package fun.kafka.v1.producer;

import fun.kafka.v1.config.ProducerConfigPro;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class ProductService {

    public void sendMessage(String topic, Object o) {
        Producer<String, String> producer = ProducerConfigPro.createProducer();
        ProducerRecord producerRecord = new ProducerRecord<String, String>(topic, (String) o);
        // 发送消息
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception !=null){
                    if(exception instanceof RetriableException){
                        // 处理可重试的异常
                    }else{
                        // 处理不可重试的异常
                        exception.printStackTrace();
                    }
                }
                System.out.println("生产者成功发送消息到" + topic);
            }
        });
    }


}
