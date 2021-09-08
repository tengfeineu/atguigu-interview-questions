package 第二季_20_集合类的线程安全问题.代码;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author wangtf@citic.com
 * @Title: SetDemo
 * @ProjectName HighFrequencyInterviewQuestions
 * @Description: TODO
 * @date 2021/9/5 14:08
 */
public class SetDemo {
    public static void main(String[] args) {
        //Set<String> set = new HashSet<>();
        //Set<String> set  = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
        Set<String> set1 = new HashSet<>();
        set1.add("a");
    }
}
