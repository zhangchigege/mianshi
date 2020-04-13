package cn.zhangchi.collect;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合类不安全问题
 * ArrayList
 *
 * 1. 故障现像:
 *          java.util.ConcurrentModificationException
 *
 * 2. 导致原因
 *          并发争抢修改导致;
 *
 *
 *
 * 3. 解决方案
 *          3.1 new Vector<>();
 *          3.2 Collections.synchronizedList(new ArrayList<>());
 *          3.3 new CopyOnWriteArrayList<>();
 *              写时复制:CopyOnWrite容器即写时复制的容器,往一个容器添加元素的时候,不直接往容器Object[]添加,而是先将当前容器Object[]
 *            进行copy,复制出一个新的容器,Object[] newElement,然后新的容器Object[] newElements 里添加元素,添加完元素之后,再将原容器
 *            setArray(newElements);
 *              这样做的好处是可以对CopyOnWrite容器进行并发的读,而不需要加锁,因为当前容器不会添加任何元素,所以CopyOnWrite容器也是一种读写分离的思想
 *            读和写不同的容器
 *          3.4 new CopyOnWriteArraySet<>();
 *              底层同 new CopyOnWriteArrayList<>();
 *          3.5 new ConcurrentHashMap<>();
 *              Collections.synchronizedMap();
 *
 *
 化建议
 *
 *
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }


    }

    public static void mapNotSafe() {
        // Map<String, String> map = new HashMap<>();
        Map<String, String> map = Collections.synchronizedMap(Collections.emptyMap());

        for (int i = 0; i <= 30 ; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();

        }
    }

    private static void setNotSafe() {
        // Set<String> set = new HashSet<>();
        // Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i <= 30 ; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            }, String.valueOf(i)).start();

        }
    }

    private static void listNotSafe() {
        // Vector
        // List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

}
