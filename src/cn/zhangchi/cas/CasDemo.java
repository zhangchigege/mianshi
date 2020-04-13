package cn.zhangchi.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 是:  比较并交换  ===> compareAndSet
 *              是一条 CPU 并发原语,属于操作系统用于范畴,是由若干条指令组成的,用于完成某个功能的一个过程,并且原语的执行必须是连续的,在执行过程中不允许被中断,
 *          也就是说 CAS 是一条 CPU 的原子指令,不会造成所谓的数据不一致的问题;
 *              功能是判断内存某个位置的值是否为预期值;
 *
 *      底层原理:
 *              自旋锁
 *              UnSafe:
 *                          是 CAS 的核心类,由于java方法无法直接访问底层系统,需要通过本地(native)方法来访问,Unsafe 相当于一个后门,基于该类,可以直接
 *                      操作特定内存的数据;
 *                          Unsafe 类存在于 sun.misc 包中,其内部方法操作可以像 C 的指针一样直接操作内存,因为java中 CAS 操作的执行依赖于 Unsafe 类方法;
 *                          Unsafe类中的方法都直接调用操作系统底层资源执行的响应任务;
 *
 *
 *      缺点:
 *              1. 循环时间长开销较大,如果失败,会一直进行尝试;
 *              2. 只能保证一个共享变量的原子操作;
 *              3. 出现 ABA 问题 : 狸猫换太子 ;
 */
public class CasDemo {


    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2020) + "\t current data : " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data : " + atomicInteger.get());


        atomicInteger.getAndDecrement();



    }


}
