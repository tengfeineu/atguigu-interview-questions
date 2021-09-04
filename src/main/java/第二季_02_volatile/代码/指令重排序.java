package 第二季_02_volatile.代码;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wangtf@citic.com
 * @Title: 指令重排序
 * @ProjectName HighFrequencyInterviewQuestions
 * @Description: TODO
 * @date 2021/9/4 14:58
 */
public class 指令重排序 {

    static  int a, b, x, y;

    public static void main(String[] args) throws InterruptedException {

        Set<String> stringSet = new HashSet<>();
        while (true) {
            a = 0;
            b = 0;
            x = 0;
            y = 0;
            Thread t1 = new Thread() {
                @Override
                public void run() {
                    x = 1; // 1
                    a = y; // 4

                }
            };
            Thread t2 = new Thread() {
                @Override
                public void run() {
                    y = 1;  //2
                    b = x;  //3

                }
            };
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            stringSet.add("a:" + a + ",b:" + b);
            System.out.println(stringSet);
            if (a == 0 && b == 0) {
                break;
            }
        }
    }
}

