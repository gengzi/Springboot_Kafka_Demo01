package fun.gengzi.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fun.gengzi.consume.BookConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MsgConsumerService {

    private final Logger logger = LoggerFactory.getLogger(BookConsumerService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MsgProducerService service;

    public MsgConsumerService(MsgProducerService service) {
        this.service = service;
    }

    // 消费消息1
    @KafkaListener(topics = {"topic1"}, groupId = "group1")
    public Book consumeMessage1(ConsumerRecord<String, String> bookConsumerRecord) {
        try {
            Book book = objectMapper.readValue(bookConsumerRecord.value(), Book.class);
            //todo 进行业务处理
            service.sendMsg("topic2",new Book(1L,"new"));
            logger.info("消费者消费topic:{} partition:{}的消息 -> {}", bookConsumerRecord.topic(), bookConsumerRecord.partition(), book.toString());
            return book;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 消费消息2
    @KafkaListener(topics = {"topic2"}, groupId = "group1")
    public Book consumeMessage2(ConsumerRecord<String, String> bookConsumerRecord) {
        try {
            Book book = objectMapper.readValue(bookConsumerRecord.value(), Book.class);

            logger.info("消费者消费topic:{} partition:{}的消息 -> {}", bookConsumerRecord.topic(), bookConsumerRecord.partition(), book.toString());
            return book;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }



}
