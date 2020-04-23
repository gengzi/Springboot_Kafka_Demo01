package fun.gengzi.productor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    /**
     * 在Spring4.x中增加了新的特性：如果类只提供了一个带参数的构造方法，则不需要对对其内部的属性写@Autowired注解，Spring会自动为你注入属性。
     * 4.3之后的功能，如果只有一个构造方法，自动用这个构造方法注入配合lombok的@RequiredArgsConstructor使用体验很好
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//
//    public ProductService(KafkaTemplate<String, Object> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }


    /**
     * de
     *
     * @param topic
     * @param o
     */
    public void sendMessage(String topic, Object o) {
        // 发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, o);
        // 异步发送，并回调
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                // 在出现异常中，包含两种异常，一种是 不可重试的异常，一种是 可重试的异常

                logger.error("生产者发送消息：{} 失败，原因：{}", o.toString(), ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("生产者成功发送消息到" + topic + "-> " + result.getProducerRecord().value().toString());
            }
        });
        // 同步发送 , 调用 get 方法会一直等待， kafka borker 将发送结果 返回回来
        // SendResult<String, Object> sendResult = kafkaTemplate.send(topic, o).get();


    }


    /**
     * de
     *
     * @param topic
     * @param o
     */
    public void sendMessage(String topic, String key, Object o) {
        // 发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, o);
        // 异步发送，并回调
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                // 在出现异常中，包含两种异常，一种是 不可重试的异常，一种是 可重试的异常

                logger.error("生产者发送消息：{} 失败，原因：{}", o.toString(), ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("生产者成功发送消息到" + topic + "-> " + result.getProducerRecord().value().toString());
            }
        });
        // 同步发送 , 调用 get 方法会一直等待， kafka borker 将发送结果 返回回来
        // SendResult<String, Object> sendResult = kafkaTemplate.send(topic, o).get();


    }
}
