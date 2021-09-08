package 第二季_16_ABA问题.代码;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReferenceDemo {
    //演示AtomicReference类的使用
    @Test
    public void demo1() {
        User zhangSan = new User("张三", 10);
        User liSi = new User("李四", 15);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(zhangSan);//这一步别忘了

        //参数1：预期值
        //参数2：期望修改成的值
        System.out.println(atomicReference.compareAndSet(zhangSan, liSi));
        System.out.println(atomicReference.get().toString());

        System.out.println(atomicReference.compareAndSet(zhangSan, liSi));
        System.out.println(atomicReference.get().toString());

        //输出结果:
        //true
        //User{userName='李四', age=15}
        //false
        //User{userName='李四', age=15}
    }

    //演示ABA问题
    @Test
    public void demo2() {

        System.out.println("ABA问题产生");
        AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

        new Thread(() -> {
            System.out.println(atomicReference.compareAndSet(100, 101));
            System.out.println(atomicReference.compareAndSet(101, 100));
        }).start();

        if (Thread.activeCount() > 1) {
            Thread.yield();
        }
        new Thread(() -> {
            System.out.println(atomicReference.compareAndSet(100, 2021));
            System.out.println(atomicReference.get().toString());
        }).start();

    }

    @Test
    public void demo3() {
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

        int stamp = atomicStampedReference.getStamp();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 初始化当前版本号 " + stamp + " 当前值 " + atomicStampedReference.getReference());
            System.out.println(Thread.currentThread().getName() + " 第一次对换结果: " + atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1));
            System.out.println(Thread.currentThread().getName() + " 第一次对换后版本号 " + atomicStampedReference.getStamp() + " 当前值 " + atomicStampedReference.getReference());
            System.out.println(Thread.currentThread().getName() + " 第二次对换结果: " + atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println(Thread.currentThread().getName() + " 第二次对换后版本号 " + atomicStampedReference.getStamp() + " 当前值 " + atomicStampedReference.getReference());
        }, "t1").start();

        if (Thread.activeCount() > 1) {
            Thread.yield();
        }
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 第一次对换结果: " + atomicStampedReference.compareAndSet(100, 2021, stamp, stamp + 1));
            System.out.println(Thread.currentThread().getName() + " 第一次对换后版本号 " + atomicStampedReference.getStamp() + " 当前值 " + atomicStampedReference.getReference());
        }, "t2").start();

        if (Thread.activeCount() > 1) {
            Thread.yield();
        }
    }
}
