package fun.kafka.v1.config;

import fun.gengzi.productor.KafkaConstants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerConfigPro {


    /**
     * 创建
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
        // 使用自定义的value 序列化
        //properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "fun.gengzi.productor.UserInfoSerializer");

        // 配置 acks 为1 ，当borker 中的 leader 将消息持久化到本地日志中，就将发送的返回结果，返回给 生产者
        properties.put(ProducerConfig.ACKS_CONFIG,"1");

        // 配置 producer 缓存区的大小  默认 32M
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,"33554432");

        // 配置发送消息是否压缩,压缩后，会提示发送消息的吞吐量，但是需要额外的cpu 资源
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"lz4");
        // 配置发送方，能发送消息的大小
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG,10485760);

        // 配置自定义的分区机制
        //properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"fun.gengzi.productor.MyPartition");

        return new KafkaProducer<>(properties);
    }
}