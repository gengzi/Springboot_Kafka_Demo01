package fun.gengzi.test;

import fun.gengzi.consume.ConsumerCreator;
import fun.gengzi.productor.ProductService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author shuang.kou
 */
@RestController
@RequestMapping(value = "/book")
public class BookController {
//    @Value("${kafka.topic.my-topic}")
//    String myTopic;
//    @Value("${kafka.topic.my-topic2}")
//    String myTopic2;
    private final ProductService producer;
    private AtomicLong atomicLong = new AtomicLong();

    private final MsgProducerService msgProducerService;

    private final MsgConsumerService msgConsumerService;

    public BookController(ProductService producer, MsgProducerService msgProducerService, MsgConsumerService msgConsumerService) {
        this.producer = producer;
        this.msgProducerService = msgProducerService;
        this.msgConsumerService = msgConsumerService;
    }



//    BookController(ProductService producer) {
//        this.producer = producer;
//    }

//    @PostMapping
//    public void sendMessageToKafkaTopic(@RequestParam("name") String name) {
//        this.producer.sendMessage(myTopic, new Book(atomicLong.addAndGet(1), name));
//        this.producer.sendMessage(myTopic2, new Book(atomicLong.addAndGet(1), name));
//    }


    @PostMapping("/new")
    public void sendMessage(@RequestParam("name") String name) {
        // 发送消息
        this.msgProducerService.sendMsg("topic1", new Book(atomicLong.addAndGet(1), name));

    }
}