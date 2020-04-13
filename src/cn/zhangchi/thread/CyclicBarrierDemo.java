package cn.zhangchi.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier的字面意思是可循环(Cyclic)使用的屏障(Barrier);
 * 它要做的事情是,让一组线程到达一个屏障(也可以叫同步点)时被阻塞,直到最后一个线程到达屏障时,屏障才会
 * 开门,所有被屏障拦截的线程才会继续干活,线程进入屏障通过CyclicBarrier的await()方法;
 */
public class CyclicBarrierDemo {


    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("开始-----");
        });

        for (int i = 0; i <= 7; i++) {
            final int tempInt = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " \t 到第几个" + tempInt);
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }


}
