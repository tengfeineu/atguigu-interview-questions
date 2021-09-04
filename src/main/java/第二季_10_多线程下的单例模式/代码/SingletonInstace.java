package 第二季_10_多线程下的单例模式.代码;

/**
 * @author wangtf@citic.com
 * @Title: SingletonInstace
 * @ProjectName HighFrequencyInterviewQuestions
 * @Description: TODO
 * @date 2021/9/4 15:41
 */
public class SingletonInstace {

    private static volatile SingletonInstace singletonInstace = null;

    private SingletonInstace() {
        System.out.println("我是构造方法,我的名字叫 " + Thread.currentThread().getName());
    }

    //public static synchronized SingletonInstace  getInstance() {
    public static SingletonInstace getInstance() {
        if (singletonInstace == null) {
            // DCL (Double Check Lock 双端检锁机制)
            synchronized (SingletonInstace.class) {
                if (singletonInstace == null) {
                    singletonInstace = new SingletonInstace();
                }
            }
        }
        return singletonInstace;
    }

    public static void main(String[] args) {


/*        System.out.println(SingletonInstace.getInstance() == SingletonInstace.getInstance());
        System.out.println(SingletonInstace.getInstance() == SingletonInstace.getInstance());
        System.out.println(SingletonInstace.getInstance() == SingletonInstace.getInstance());*/

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                    SingletonInstace.getInstance();
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
    }
}
