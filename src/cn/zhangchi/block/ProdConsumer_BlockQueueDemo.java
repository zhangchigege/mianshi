package cn.zhangchi.block;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * sync和lock区别:
 *      1.原始构成:
 *          sync是关键字属于JVM层面
 *              monitorenter(底层是通过monitor对象来完成,其实wait/notify等方法也依赖于monitor对象只有在同步块或方法中才能调用wait/notify等方法;
 *              nonitorexit 退出
 *          Lock是具体类,是 api层面的锁
 *
 *      2.使用方法
 *          sync 不需要用户去手动释放锁,当sync代码执行完成后系统会自动让线程释放对锁的占用;
 *          ReentrantLock 则需要用户去手动释放锁,若没有主动释放锁,就有可能导致出现死锁现像;
 *          需要lock() 和 unLock方法配合 try/finall语句块完成;
 *
 *      3.等待是否可中断
 *          sync 不可中断,除非抛出异常或者正常运行完成;
 *          reentrantLock 可中断,1:设置超时方法啊tryLock(Long timeout, TimeUnie unit), 2.lockUbterruptibly()放代码块中,调用interrpt()方法可中断;
 *
 *      4.加锁是否公平
 *          sync非公平锁;
 *          ReentrantLock 两者都可以,默认非公平锁,构造方法可以传入 boolean值,true为公平锁,false为非公平锁;
 *
 *      5.锁绑定多个条件Condition
 *          sync没有
 *          ReentrantLock用来实现分组唤醒需要唤醒的线程,可以精确唤醒,而不像sync要么随机唤醒一个线程要么唤醒全部线程;
 *
 */
public class ProdConsumer_BlockQueueDemo {

    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始生产");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始消费");
            try {
                myResource.myConsumer();
                System.out.println();
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("5秒时间,线程停止,活动结束");

        myResource.stop();

    }

    public static void reentrantLockMethod() {
        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                shareResource.print5();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                shareResource.print10();
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                shareResource.print15();
            }
        },"C").start();
    }


}
class ShareResource {
    private int number = 1;

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    public void print5(){
        lock.lock();
        try {
            // 1判断
            while ( number != 1) {
                condition1.await();
            }
            // 2干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            // 3通知
            number = 2;
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            // 1判断
            while ( number != 2) {
                condition2.await();
            }
            // 2干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            // 3通知
            number = 3;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            // 1判断
            while ( number != 3) {
                condition3.await();
            }
            // 2干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            // 3通知
            number = 1;
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }



}


class MyResource {
    // 可见性
    private volatile boolean FLAG = true;// 默认开启,进行生成+消费
    // 原子性
    private AtomicInteger atomicInteger = new AtomicInteger();

    // 通顺,适配,通用
    BlockingQueue<String> blockingQueue = null;
    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws Exception{
        String data = null;
        boolean retuValue;

        while (FLAG) {
            data = atomicInteger.incrementAndGet()+""; // 原子++
            retuValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS); // 2秒取一个
            if (retuValue) {
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+ data + "成功");
            }else {
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+ data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 停止,表示FLAG 为false,生成结束" );
    }

    // 消费
    public void myConsumer() throws Exception{

        String result = null;

        while (FLAG){
            result = blockingQueue.poll(2L,TimeUnit.SECONDS);

            if (null == result || result.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒钟没有取到,消费退出");
                System.out.println();
                return;
            }

            System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
        }
    }

    // 停止方法
    public void stop() throws Exception {
        this.FLAG = false;
    }
}
