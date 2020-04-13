package cn.zhangchi.block;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 */
public class SyncHronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();


        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " \t put 1");
                blockingQueue.put("a");
                System.out.println(Thread.currentThread().getName() + " \t put 2");
                blockingQueue.put("b");
                System.out.println(Thread.currentThread().getName() + " \t put 3");
                blockingQueue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+ "\t" + blockingQueue.take());TimeUnit.SECONDS.sleep(5);
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t" + blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+ "\t" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();

    }
}
