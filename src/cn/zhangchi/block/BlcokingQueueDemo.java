package cn.zhangchi.block;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue: 是一个基于数组结构的有界阻塞队列,此队列按 FIFO(先进先出) 原则对元素进行排序;
 * LinkedBlockingQueue: 一个基于链表结构的阻塞队列,此队列按 FIFO(先进先出) 排序元素,吞吐量通常要高于  ArrayBlockingQueue;
 * SynchronousQueue: 一个不存储元素的阻塞队列,每个插入操作必须等到另一个线程调用移除操作,否则插入操作一直处于阻塞状态,吞吐量通常要高于 LinkedBlockingQueue;
 *
 * 1 队列:
 *
 * 2 阻塞队列:
 *
 *      当阻塞队列是空时,从队列中获取元素的操作将会被阻塞;
 *      当阻塞队列是满时,往队列里添加元素的操作将会被阻塞;
 *
 *      试图从空的阻塞队列中获取元素的线程将会被阻塞,知道其他的线程往空的队列插入新的元素;
 *      同样,试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞,直到其他的线程从列中移除一个或者多个元素或者完全清空队列后使队列重新变得空闲起来并后续新增;
 *      2.1 阻塞队列有没有好的一面
 *
 *      2.2 不得不阻塞,如何管理
 *
 *
 */
public class BlcokingQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        timeOut(blockingQueue);

    }

    public static void timeOut(BlockingQueue<String> blockingQueue) throws InterruptedException {
        // 超时
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2L, TimeUnit.SECONDS));
        // 阻塞设置时间后返回false
        System.out.println(blockingQueue.offer("d", 2L, TimeUnit.SECONDS));
    }

    public static void blockThread(BlockingQueue<String> blockingQueue) throws InterruptedException {
        // 当队列满的时候满,阻塞线程
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        //blockingQueue.put("e");


        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        // 当队列为空时阻塞线程
        //blockingQueue.take();
    }

    public static void NoThrowsExce(BlockingQueue<String> blockingQueue) {
        // offer:成功 true, 失败false,不报异常
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));

        // 检出队首元素
        System.out.println(blockingQueue.peek());

        // 超出返回 null
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }

    public static void throwsExce() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // 抛出异常
        // System.out.println(blockingQueue.add("x")); Exception in thread "main" java.lang.IllegalStateException: Queue full

        // 检查队首元素
        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // 抛出异常
        // System.out.println(blockingQueue.remove()); Exception in thread "main" java.util.NoSuchElementException
    }


}
