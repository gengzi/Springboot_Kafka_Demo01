package fun.gengzi.test;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

/**
 * 主线程等待其他线程执行完毕，再执行
 */
public class Concurrentprocessing {


    @SneakyThrows
    public static void main(String[] args) {
        int count = 0;
        // 创建计数器
        CountDownLatch countDownLatch = new CountDownLatch(100);
        // 执行其他线程
        OtherRun otherRun = new OtherRun(countDownLatch, count);
        for (int i = 0; i < 100; i++) {
            new Thread(otherRun).start();
        }
        // 等待其他线程执行完毕
        countDownLatch.await();
        System.out.println("执行主线程");


    }


}

class OtherRun implements Runnable {
    private CountDownLatch downLatch;
    private volatile int count = 0;

    public OtherRun(CountDownLatch downLatch, int count) {
        this.downLatch = downLatch;
        this.count = count;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (downLatch) {
            // 执行一些事情
            count += 1;
            System.out.println("执行" + count);
            // 将计数器减1
            downLatch.countDown();
        }
    }
}
