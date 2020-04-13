package cn.zhangchi.thread;

import cn.zhangchi.enu.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch ： 计数器
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {

            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 国,被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEach_CountryEnum(i).getValue()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 秦统一");
    }

    public static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i <= 6; i++) {

            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 离开");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 最后一个离开");
    }
}
