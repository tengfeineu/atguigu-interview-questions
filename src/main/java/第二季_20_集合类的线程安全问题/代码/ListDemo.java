package 第二季_20_集合类的线程安全问题.代码;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class ListDemo {

    public static void main(String[] args) {
        //List<String> list = new ArrayList<>();
        //List<String> list = new Vector<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

        // 多次运行会出现 java.util.ConcurrentModificationException
    }

    //演示集合类的并发安全问题
    @Test
    public void demo() throws InterruptedException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            try {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                Thread thread = new Thread(() -> {
                    list.add(UUID.randomUUID().toString().substring(0, 8));
                    System.out.println("当前list: " + list);
                    countDownLatch.countDown();
                });
                thread.start();
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //演示集合类的并发安全问题
    @Test
    public void demo1() {
        List<String> list = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(String.valueOf(random.nextInt()));
                System.out.println(list);
            }).start();
        }

        //出现异常：Exception in thread "Thread-0" java.util.ConcurrentModificationException
    }

    //使用Vector类
    //Vector类继承自AbstractList类，AbstractList类实现了List接口
    @Test
    public void demo2() {
        List<String> list = new Vector<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(String.valueOf(random.nextInt()));
                System.out.println(list);
            }).start();
        }
    }

    //使用Collections.synchronizedList()方法，参数为一个list
    @Test
    public void demo3() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(String.valueOf(random.nextInt()));
                System.out.println(list);
            }).start();
        }
    }

    //使用CopyOnWriteArrayList类
    @Test
    public void demo4() {
        List<String> list = new CopyOnWriteArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(String.valueOf(random.nextInt()));
                System.out.println(list);
            }).start();
        }
    }
}
