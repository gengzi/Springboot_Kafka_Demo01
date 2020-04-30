package fun.gengzi.test;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

/**
 * 其他线程等待门打开，再执行
 */
public class EmergencyDoor {

    @SneakyThrows
    public static void main(String[] args) {
        // 创建门
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 创建其他线程
        OtherRunnable otherRunnable = new OtherRunnable(countDownLatch);
        for (int i = 0; i < 100; i++) {
            new Thread(otherRunnable).start();
        }
        System.out.println("等待30秒");
        // 等待三十秒
        Thread.sleep(30000);
        // 打开门，现在其他线程就开始执行了
        countDownLatch.countDown();

    }


}

class OtherRunnable implements Runnable {
    private CountDownLatch downLatch;
    private volatile int count = 0;

    public OtherRunnable(CountDownLatch downLatch) {
        this.downLatch = downLatch;
    }

    @SneakyThrows
    @Override
    public void run() {
        downLatch.await();
        // 执行一些事情
        System.out.println("执行" + (count++));

    }
}
