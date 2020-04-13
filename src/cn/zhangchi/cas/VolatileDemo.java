package cn.zhangchi.cas;

import java.util.concurrent.TimeUnit;

/**
 * 1.volatile是java虚拟机提供的轻量级的同步机制(轻量级的sync)
 *      1.1 保证可见性(及时通知机制)
 *              由于JVM运行程序的实体是线程,二每个线程创建时,JVM都会为其创建一个工作内存(栈空间),工作内存是每个线程的私有
 *          数据区域,而java内存模型中规定所有变量都存储在主内存,主内存是共享内存区域,所有线程都可以访问,但线程对变量的
 *          操作(读取赋值操作等)必须在工作内存中进行,首先将变量从主内存拷贝到自己的工作内存空间,然后对变量进行操作,操作
 *          完成后再将变量写回主内存,不能直接操作主内存中的变量,各个线程中的工作内存中存储这主内存中的变量副本拷贝,因此
 *          不同的线程间无法访问对方的工作内存,线程间的通信(传值)必须通过主内存来完成;
 *
 *      1.2 不保证原子性
 *              原子性是指不可分割,完整性,即某个线程正在做某个具体业务时,中间不可被加塞或者被分割,需要整体完成,要么同时完成,要么同时失败;
 *              因为出现了丢失写值的现像
 *
 *      1.3 禁止指令重排
 *              计算机在执行程序时,为了提高性能,编译器和处理器常常会对指令做重排,分为: 源代码 -> 编译其优化的重排 -> 指令并行的重排 -> 内存系统的重排 -> 最终执行的指令
 *              单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致;
 *              处理器在进行重排时必须要考虑指令之间的数据依赖性;
 *              多线程环境中线程交替执行,由于编译器重排的存在,两个线程中使用的变量使用的变量能否保持一致性时无法确定的,结果无法预测;
 *              volatile实现禁止指令重排的优化,从而避免多线程环境下程序出现乱序执行的现像;
 *              由于编译器和处理器都能执行指令重排优化,如果在指令间插入一条内存屏障则会告诉编译器和cpu,不管什么指令都不能和这条内存屏障指令重排序,也就是说通过插入内存屏障禁止在内存屏障签后的指令执行重排序优化,内存屏障另外一个作用是强制输出各种cpu的缓存数据,因此任何cpu上的线程候能读取到这些数据的最新版本
 *
 * 2.JMM规范(java 内存模型 java Memory Model )本身是一种抽象的概念,并不真实存在,是一组规则或规范,通过这组规范
 * 定义了程序中各个变量(包括实例字段,静态字段和构成数组对象的元素)的访问方式
 * 线程解锁前,必须把共享变量的值刷新回主内存
 * 线程加锁前,必须读取主内存的最新值到自己的工作内存
 * 加解锁是同一把锁
 *      2.1 可见性
 *      2.2 原子性
 *      2.3 有序性
 *
 *
 */
public class VolatileDemo {



    public static void main(String[] args) {


    }

    private static void UnSort() {
        MyData myData = new MyData();

        for (int i = 0 ; i <= 20; i++) {
            new Thread(() ->{
                for (int j = 0; j <= 1000; j++) {
                    myData.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }

        /*try {
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        // 需要等待上面线程全部计算完成后再用main线程取得最后的结果值
        while (Thread.activeCount() > 2) { // 控制多线程运算时间
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+" \t finally number value :" + myData.number );
    }

    // 可见性,及时通知其他线程,主物理内存的值被修改
    private static void seeOkByVolatile() {
        // 验证可见性
        MyData myData = new MyData(); // 线程操作资源类

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " \t come in");
              // 暂停线程
            try {
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + " \t updated number value " + myData.number);
        },"AAA").start();


        // main线程
        while (myData.number == 0) {
            // main线程一直等待循环,知道number值不再等于零
        }

        System.out.println(Thread.currentThread().getName() + " \t mission is over, main get number value " + myData.number);
    }
}



class MyData{
    // 没有添加volatile关键字,没有可见性
    volatile int number = 0;

    public void addTo60(){
        this.number = 60;
    }

    // 此使number前面添加了关键子修饰,不保证原子性
    public  void addPlusPlus(){
        number++;
    }
}












