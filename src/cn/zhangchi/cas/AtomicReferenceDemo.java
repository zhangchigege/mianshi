package cn.zhangchi.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用更新
 *
 * AtomicReference:解决 ABA 问题
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        User z3 = new User("z3", 22);
        User l4 = new User("l4", 25);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString());
    }


}

class User {
    String userName;
    int age;

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
