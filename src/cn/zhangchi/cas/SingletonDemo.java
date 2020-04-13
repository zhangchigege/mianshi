package cn.zhangchi.cas;

public class SingletonDemo {

    private static volatile SingletonDemo instance ;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法");
    }


    // DCL模式(Double Check Lock 双端检锁机制)
    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {
        // 单线程(main)操作单例模式
        // System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        // 并发多线程后,情况发生很大变化
        for (int i = 0; i <= 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }


}
