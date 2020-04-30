import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class MyTest {

    private static void accept(Integer aa) {
        if (aa > 2) System.out.println(aa);
    }

    /**
     * 集合的foreach
     */
    @Test
    public void fun01() {
        // List
        List<Integer> integers = Arrays.asList(1, 2, 3);
        integers.forEach(aa -> {
            if (aa > 2) {
                System.out.println(aa);
            }
        });
        // 类名 ： 静态方法
        integers.forEach(MyTest::accept);
        // 类名 ： 静态方法
        integers.forEach(System.out::println);

        // Map
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "ffff");
        // lambda 表达式
        map.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
    }

    /**
     * 线程的创建
     */
    @Test
    public void fun02() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        }).start();

        // 线程创建
        new Thread(() -> System.out.println("hello world")).start();
    }


    @Test
    public void fun03(){

        CountDownLatch countDownLatch = new CountDownLatch(2);


    }


}
