package cn.zhangchi.gc;

/**
 * JVM的参数类型:
 *          查看java运行程序: jps -l
 *          查看运行中java JVM参数信息: jinfo
 *          查看jvn原始配置：java -XX:+PrintFlagsFinal -version
 *              1.标配参数:
 *                      -version
 *                      -help
 *                      java -showversion
 *              2.X参数:
 *                      -Xint --解释执行
 *                      -Xcomp  --第一次使用就编译成本地代码
 *                      -Xmixed --混合模式
 *              3.XX参数:
 *                      Boolean类型:
 *                                  -XX: + 或者 - 某个属性值
 *                                  + 表示开启
 *                                  - 表示关闭
 *                                  是否打印GC收集细节:  jinfo -flag [parems] PID
 *                      KV设值类型:
 *                                  -XX:属性 key = 属性值 value  ----  -XX:MetaspaceSize=21807104
 *
 *
 *               常用参数:
 *                      -Xms : 初始大小内存,默认为物理内存 1/64,等价于 -XX:InitialHeapSize
 *                      -Xmx : 最大分配内存,默认为物理内存 1/4,等价于 -XX:MaxHeapSize
 *                      -Xss : 设置单个线程栈的大小,一半默认为 512k~1024k,等价于 -XX:ThreadStackSize
 *                      -Xmn : 设置年轻代大小
 *                      -XX:MetaspaceSize : 设置元空间大小,元空间的本质和永久代类似,都是对JVM规范中方法区的实现,不过元空间与永久代之间最大的区别在于:元空间
 *                      并不在虚拟机中,而是使用本地内存,因此,默认情况下,元空间的大小仅受本地内存限制;
 *                              -Xms128m -Xmx4096m -Xss1024k -XXMetaspaceSize=512m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseSerialGC
 *                      -XX: PrintGCDetails : 输出GC详细信息
 */
public class GcDemo {

    public static void main(String[] args) throws InterruptedException {

        long memory = Runtime.getRuntime().totalMemory(); // 返回java虚拟机的内存总量
        long maxMemory = Runtime.getRuntime().maxMemory(); // 返回 java 虚拟机试图使用的最大内存量
        System.out.println(" TOTAL_MEMORY(-Xms) = " + memory + " (字节) " + (memory / (double)1024 / 1024) + " MB");
        System.out.println(" TOTAL_MEMORY(-Xmx) = " + maxMemory + " (字节) " + (maxMemory / (double)1024 / 1024) + " MB");


    }
}
