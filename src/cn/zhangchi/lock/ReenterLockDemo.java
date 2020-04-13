package cn.zhangchi.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁(递归锁)
 * <p>
 * 指的是同一线程外层函数获得锁之后,内层递归函数仍能获取该锁的代码,在同一个线程在外层方法获取锁的时候,在进入内层方法会自动获取锁;
 * 线程可以进入任何一个它已经拥有的锁所同步着的代码块;
 * <p>
 * <p>
 * 12	 invoked sendSMS()
 * 12	 invoked sendEmail()
 * 13	 invoked sendSMS()
 * 13	 invoked sendEmail()
 */
public class ReenterLockDemo {

    public static void main(String[] args) {
        Phone p = new Phone();

        new Thread(() -> {
            try {
                p.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                p.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Thread thread3 = new Thread(p, "t3");
        Thread thread4 = new Thread(p, "t4");

        thread3.start();
        thread4.start();


    }


}

// 线程操作资源类
class Phone implements Runnable {
    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getId() + "\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception {
        System.out.println(Thread.currentThread().getId() + "\t invoked sendEmail()");
    }

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {

        lock.lock();

        try {
            System.out.println(Thread.currentThread().getId() + "\t invoked get()");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void set() {

        lock.lock();

        try {
            System.out.println(Thread.currentThread().getId() + "\t invoked set()");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}