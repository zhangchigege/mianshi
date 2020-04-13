package cn.zhangchi.ref;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * 弱引用缓存实现
 */
public class WeakHashMapDemo {

    public static void main(String[] args) {
        MyHashMap();
        System.out.println("====================");
        myWeakHashMap();
    }

    private static void myWeakHashMap() {
        WeakHashMap<Integer, String> map = new WeakHashMap<>();

        Integer key = new Integer(2);
        String value = "WeakHashMap";

        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);

    }

    private static void MyHashMap() {

        HashMap<Integer, String> map = new HashMap<>();

        Integer key = new Integer(1);
        String value = "HashMap";

        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);


    }
}
