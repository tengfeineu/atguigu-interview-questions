package 第二季_12_CAS;

import java.util.concurrent.atomic.AtomicInteger;

public class CasDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "  " + atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 1024) + "  " + atomicInteger.get());
        atomicInteger.getAndIncrement();
    }
}
