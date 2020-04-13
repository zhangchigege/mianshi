package cn.zhangchi.ref;

import java.lang.ref.SoftReference;

/**
 * 软引用
 * <p>
 * 软引用是一种相对强引用弱化了一些的引用,需要用 java.lang.ref.SofrReference 类来实现,可以让对象豁免一些垃圾收集;
 * <p>
 * 对于只有软引用的对象来说:
 * 当系统内存充足时,不会被回收;
 * 当系统内存不足时,会被回收;
 * <p>
 * 软引用通常用在对内存敏感的程序中,比如告诉缓存就有用到软引用,内存够用的时候就保留,不够用就回收;
 */
public class SoftReferenceDemo {

    public static void main(String[] args) {
        softRef_Memory_NotEnough();
    }

    /**
     * 内存够用时
     */
    public static void sofrRef_Memory_Enough() {
        Object object1 = new Object();

        SoftReference<Object> softReference = new SoftReference<>(object1);

        System.out.println(object1);
        System.out.println(softReference.get());

        object1 = null;
        System.gc();

        System.out.println(object1);
        System.out.println(softReference.get());

    }

    /**
     * 内存不够用
     */
    public static void softRef_Memory_NotEnough() {
        Object object1 = new Object();

        SoftReference<Object> softReference = new SoftReference<>(object1);

        System.out.println(object1);
        System.out.println(softReference.get());


        object1 = null;


        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(object1);
            System.out.println(softReference.get());
        }
    }


}
