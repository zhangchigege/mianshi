package cn.zhangchi.threadPool;

import java.util.concurrent.*;

/**
 * 线程池构造参数
 * <p>
 * int corePoolSize : 线程池中的常驻核心线程数;
 * int maximumPoolSize : 线程池能够容纳同时执行的最大线程数,比值必须大于等于1;
 * long keepAliveTime : 多余的空闲线程的存活时间;
 *                      当前线程池数量超国 corePoolSize 时,当空闲时间达到 keepAliveTime 值时,多余空闲线程会被销毁直到只剩下 corePoolSize 个线程为止;
 * TimeUnit unit : 设置时间单位;
 * BlockingQueue<Runnable> workQueue : 阻塞队列,任务队列,被提交但尚未被执行的任务;
 * ThreadFactory threadFactory : 表示生成线程池中工作线程的线程工厂,用于创建线程一般用默认的即可;
 * RejectedExecutionHandler handler : 拒绝策略,表示当队列蛮子并且工作线程大于等于线程池的最大线程数 (maximumPoolSize);
 *                                  AbortPolicy: 直接抛出 RejectedExecptioin 异常阻止系统正常运行;
 *                                  CallerRunsPolicy: "调用者运行"一种调节机制,该策略既不会抛弃任务,也不会抛出异常,而是将某些任务回退到调用者;
 *                                  DiscardOldestPolicy: 抛弃队列中等待最久的任务,然后把当前任务加入队列中尝试再次提交当前任务;
 *                                  DiscardPolicy: 直接丢弃任务,不予任何处理也不抛出异常,如果允许任务丢失,这是最好的一种方案;
 */
public class ThreadPoolCon {

    public static void main(String[] args) {

        // 创建线程池
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

    }
}
