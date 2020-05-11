package fun.kafka.v1.test;


import fun.kafka.v1.consumer.ConsumerService;
import fun.kafka.v1.producer.ProductService;


public class MsgExec {


    public void sendMsg() {
        ProductService productService = new ProductService();
        for (int i = 0; i < 100000; i++) {
            productService.sendMessage("v1-topic3", "hello" + i);
        }

    }

    public void consumMsg() {
        ConsumerService consumerService = new ConsumerService();
        consumerService.consumerMsg();
    }
    //测试

    public static void main(String[] args) {
        MsgExec msgExec = new MsgExec();
        msgExec.sendMsg();
//        msgExec.consumMsg();
    }


}
