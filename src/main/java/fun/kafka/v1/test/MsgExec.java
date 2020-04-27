package fun.kafka.v1.test;


import fun.kafka.v1.consumer.ConsumerService;
import fun.kafka.v1.producer.ProductService;


public class MsgExec {


    public void sendMsg() {
        ProductService productService = new ProductService();
        productService.sendMessage("v1-topic1", "hello");
    }

    public void consumMsg() {
        ConsumerService consumerService = new ConsumerService();
        consumerService.consumerMsg();
    }


    public static void main(String[] args) {
        MsgExec msgExec = new MsgExec();
        msgExec.sendMsg();
        msgExec.consumMsg();
    }


}
