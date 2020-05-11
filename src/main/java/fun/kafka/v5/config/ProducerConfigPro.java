package fun.kafka.v5.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerConfigPro {


    /**
     * 创建
     *
     * @return
     */
    public static Producer<String, String> createProducer() {
        Properties properties = new Properties();
        // 设置连接的 kafka broker 服务器地址 ，host:port  通常是一组，可以不用设置全部的 kafka 服务器地址，只要连接上某一个节点就可以了
        // 但是为了在连接服务器，万一当前连接的服务器出现问题。导致连接不上，所以还是多配置几个
        // 必须指定
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //
//        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "client0");
        // 指定key 序列化器，因为发送到 borker 都是字节数组，需要将发送的key 序列化，这里使用的是 string类型 序列化
        // 必须指定
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // value 的序列化
        // 必须指定
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 指定 transactional.id
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transactional_id_1");

        return new KafkaProducer<>(properties);
    }
}