package fun.kafka.v1.config;

import fun.gengzi.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.omg.CORBA.TRANSACTION_MODE;

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
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
        // 必须指定，指定消费者组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "v1-consumer-group");
        // 必须指定，指定key反序列化器 可以自定义反序列化器
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 必须指定，指定value反序列化器 可以自定义反序列化器
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // 其他配置
        // 指定 coordinator 协调人 检测消费者组成员失败的时间，默认10 秒 。 检测consumer 组成员崩溃，会将该成员踢出
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,10000);
        // 最大逻辑处理时间，假如 消费者接受到消息，需要将消息进行清理，入库，需要一分钟。 就可以设置这个最大逻辑处理时间。
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,60*1000);
        // 无位移信息或者位移越界的策略，只是针对 consumer 消费的消息，不在当前消息日志的合理范围上，才会触发
        // erlist 从最早的消息，开始消费
        // latest 从最新处开始消费
        // none 抛异常
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        // 是否自动提交位移信息，设置true comsumer 会在后台自动提交位移，设置false ，需要用户手动提交位移信息。 对于需要精确一次处理的，可以设置为 false
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // consumer 单次接受数据的最大字节数  默认 50 * 1024 * 1024
        properties.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG,50 * 1024 * 1024);
        // 控制单次poll 调用返回的最大消息数  比较极端的做法就是 一次获取一条数据
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,1000);
        // 通知consumer 组中成员，需要进行新一轮的 rebalance  ，比较推荐的做法是 设置一个比较小的值，必须小于session.timeout.ms
        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,1000);
        // kafka 定期关闭空闲 socket 的时间，默认9分钟，那消费者再次请求数据的时候，会重新创建 连接，连接到borker
        // 实际环境下，如果不在乎这些 socket 资源的开销，推荐设置参数为 -1 ，不关闭空闲连接
        properties.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG,-1);

        return properties;
    }

}
