package cn.zhangchi.thread;

import java.util.concurrent.TimeUnit;

/**
 * 死锁: 死锁是指两个或两个以上的进程在执行过程中,因争夺资源而造成的一种互相等待的现像,若无外力干涉,那他们都将无法推进下去;
 *
 *
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA,lockB) ,"AAA").start();
        new Thread(new HoldLockThread(lockB,lockA) ,"BBB").start();
    }
}

// 资源类
class HoldLockThread implements Runnable{

    private String lockA;

    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName()+"\t 自己持有" + lockA + "\t 尝试获取" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有" + lockB + "\t 尝试获取" + lockA);

            }
        }

    }
}