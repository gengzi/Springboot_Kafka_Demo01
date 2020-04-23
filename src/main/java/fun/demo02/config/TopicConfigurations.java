package fun.demo02.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

// 配置类
@Configuration
// 读取yml kafka 开头的配置信息
@ConfigurationProperties(prefix = "kafka")
// 提供公共的 get set 方法
@Setter
@Getter
@ToString
class TopicConfigurations {
    // topics 注入到这个liset 中
    private List<Topic> topics;

    @Setter
    @Getter
    @ToString
    // 静态内部类， 创建的topic 对象，仅仅被TopicConfigurations 使用，不想被其他类使用，所以设置为 内部类， 而 topic 不使用外部类的 属性和方法，所以被设置为 静态的
    static class Topic {
        String name;
        Integer numPartitions = 3;
        Short replicationFactor = 1;

        NewTopic toNewTopic() {
            return new NewTopic(this.name, this.numPartitions, this.replicationFactor);
        }

    }
}
