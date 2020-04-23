package fun.demo02.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author shuang.kou
 */
@Configuration
public class TopicAdministrator {
    private final TopicConfigurations configurations;
    private final GenericWebApplicationContext context;

    // 使用构造方法的形式，代替 @autowried  spring4.3
    public TopicAdministrator(TopicConfigurations configurations, GenericWebApplicationContext genericContext) {
        this.configurations = configurations;
        this.context = genericContext;
    }

    // bean 的生命周期的方法
    // @PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行。
    // @PostConstruct注解的方法将会在依赖注入完成后被自动调用
    @PostConstruct
    public void init() {
        initializeBeans(configurations.getTopics());
    }

    private void initializeBeans(List<TopicConfigurations.Topic> topics) {
        // 使用 GenericWebApplicationContext 注入bean ， 提供 bean名称，bean 类型， 和构造bean 的对象
        topics.forEach(t -> context.registerBean(t.name, NewTopic.class, t::toNewTopic));

    }



}
