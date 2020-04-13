package cn.zhangchi.ref;

/**
 * 强引用
 */
public class StrongReferenceDemo {

    public static void main(String[] args) {
        Object obj = new Object();
        Object obj2 = obj;

        obj = null;

        System.gc();

        System.out.println(obj2);
    }


}
