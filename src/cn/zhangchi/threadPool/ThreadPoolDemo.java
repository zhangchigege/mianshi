package cn.zhangchi.threadPool;

import java.util.concurrent.*;

/**
 * 线程池使用
 *      线程池的工作主要是控制运行的线程的数量,处理过程中将任务放入队列中,然后在线程创建后启动这些任务,如果线程数量超过了最
 *      大数量超出数量的线程拍对等候,等其他线程执行完毕,再从队列中取出任务来执行;
 *
 *      主要特点:线程复用,控制最大并发数,管理线程;
 *
 *      1.降低资源消耗,通过复用利用已创建的线程降低线程创建和销毁造成的消耗;
 *      2.提高响应速度,当任务到达时,任务可以不需要等到线程创建就能立即执行;
 *      3.提高线程的可管理性,线程时稀缺资源,如果无限制的创建,不仅会消耗系统资源,还会降低系统的稳定性,使用线程池可以进行统一的分配,调优和监控;
 *
 *
 */
public class ThreadPoolDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {



        // Executors
        // ThreadPoolExecutor

        // newFixedThreadPool 执行长期任务的线程池,固定线程数,性能好很多
        // newSingleThreadExecutor 一个任务一个线程
        // newCachedThreadPool 使用:执行很多短期异步的小程序或者负载较轻的系统
        // ExecutorService threadPool = Executors.newFixedThreadPool(5); // 一池5线程
        // ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 一池1线程
        ExecutorService threadPool = Executors.newCachedThreadPool(); // 一池N线程

        try {

            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                //   TimeUnit.MILLISECONDS.sleep(200);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

    }

    public static void threadCreate() throws InterruptedException, ExecutionException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println(Thread.currentThread().getName() + "\t 执行");

        System.out.println(Thread.currentThread().getName() + "\t 执行获取的结果" + futureTask.get());
    }
}

class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("使用 Callable 创建线程");
        return 1024;
    }
}
