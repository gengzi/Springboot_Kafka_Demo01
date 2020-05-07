package fun.kafka.v3.example;

import fun.kafka.v3.common.Bar1;
import fun.kafka.v3.common.Foo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Gary Russell
 * @since 2.2.1
 */
@RestController
public class Controller {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {
        this.template.send("foos", new Foo1(what));
    }

    @PostMapping(path = "/send/bar/{what}")
    public void sendBar(@PathVariable String what) {
        this.template.send("bars", new Bar1(what));
    }

    @PostMapping(path = "/send/unknown/{what}")
    public void sendUnknown(@PathVariable String what) {
        this.template.send("bars", what);
    }

}