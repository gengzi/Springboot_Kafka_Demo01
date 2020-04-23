package fun.gengzi.productor;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *  自定义的 Partitioner 分区机制
 *  需求： 把消息key 为 audit 的消息，都发送到 topic 最后一个分区中
 */
public class MyPartition implements Partitioner {

    Random random;
    /**
     * Compute the partition for the given record.
     *
     * @param topic      The topic name  topic 名称
     * @param key        The key to partition on (or null if no key)  消息键 或者 null
     * @param keyBytes   The serialized key to partition on( or null if no key) 消息键序列化字节数组 或 null
     * @param value      The value to partition on or null  消息体 或者 null
     * @param valueBytes The serialized value to partition on or null 消息体序列化字节数组 或者 null
     * @param cluster    The current cluster metadata 集群元数据
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 获取消息键
        String  keyStr = (String) key;
        // 获取当前topic 的所有分区信息
        List<PartitionInfo> partitionInfos = cluster.availablePartitionsForTopic(topic);
        // 判断当前的 key 是否为 audit
//        if("audit".equals(keyStr)){
//            return partitionInfos.size()-1;
//        }
//
//        return random.nextInt(partitionInfos.size()-1);
        return keyStr == null || keyStr.isEmpty() || !"audit".equals(keyStr) ? random.nextInt(partitionInfos.size() -1): partitionInfos.size()-1;
    }

    /**
     * This is called when partitioner is closed.
     */
    @Override
    public void close() {
        // 该方法实现必要资源的清理工作
    }

    /**
     * Configure this class with the given key-value pairs
     *
     * @param configs
     */
    @Override
    public void configure(Map<String, ?> configs) {
        // 该方法实现必要资源的初始化工作
        random = new Random();

    }
}
