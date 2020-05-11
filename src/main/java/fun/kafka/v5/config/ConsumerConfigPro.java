package fun.kafka.v5.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

/**
 * 消费者配置
 */
public class ConsumerConfigPro {

    /**
     * 返回消费者配置
     *
     * @return
     */
    public Properties getConsumerConfig() {
        Properties properties = new Properties();
        // 必须指定，指定订阅的服务地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 必须指定，指定消费者组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "v1-consumer-group");
        // 必须指定，指定key反序列化器 可以自定义反序列化器
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 必须指定，指定value反序列化器 可以自定义反序列化器
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 是否自动提交位移信息，设置true comsumer 会在后台自动提交位移，设置false ，需要用户手动提交位移信息。
        // 对于需要精确一次处理的，可以设置为 false
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 设置隔离级别 读已提交
        properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,"read_committed");
        return properties;
    }

}
