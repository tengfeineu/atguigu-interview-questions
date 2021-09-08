> 视频P12到P15

# CAS（CompareAndSwap）

比较当前工作内存中的值和主内存中的值，如果相同则执行规定操作，否则继续比较知道主内存和工作内存中的值一致为止。

# CAS应用

CAS有3个操作数，内存之V，旧的预期值A，要修改的更新值B。
当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。

# CAS compare and swap 比较并交换

是一条CPU原语。功能是判断某个内存的值是否为预期值，如果是则更新为新的值，这个操作是原子性的  
比较工作内存中的值与主内存中的值，如果相同则执行操作，否则取出后继续比较继续比较知道相同

# AtomicInteger类getAndIncrement()源代码
```
public final int getAndIncrement() {
    return unsafe.getAndAddInt(this, valueOffset, 1);
}
```
Unsafe类getAndAddInt()参数列表
- this：当前对象

- valueOffset：内存偏移量，内存地址

  

# native关键字修饰的方法

被native关键字修饰的方法叫做本地方法，本地方法和其它方法不一样，本地方法意味着和平台有关，因此使用了native的程序可移植性都不太高。另外native方法在JVM中运行时数据区也和其它方法不一样，它有专门的本地方法栈。native方法主要用于加载文件和动态链接库，由于Java语言无法访问操作系统底层信息（比如：底层硬件设备等），这时候就需要借助C语言来完成了。被native修饰的方法可以被C语言重写。  

# Unsafe类

是CAS的核心类，由于Java方法无法直接访问底层操作系统，需要通过本地方法来访问。Unsafe类相当于一个后门，基于该类可以直接操作特定内存的数据。Unsafe类在sun.misc包中，其内部方法操作可以像C的指针一样直接操作内存  
Unsafe类中的很多方法都是native修饰的

CAS并发原语体现在Java中就是Unsafe类中的各个方法。调用Unsafe类中的CAS方法，JVM会帮我们是先出CAS汇编指令。这是一种完全依赖于硬件的功能，通过它实现了原子操作。原语属于操作系统用语范畴，是由若干条指令组成的，用于完成某个功能的一个剁成，并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说CAS是一条CPU的原子指令，不会造成的数据不一致问题  
Unsafe类的getAndAddInt()源码

![unsafe-getAndAddInt](E:\study\atguigu-interview-questions\src\main\java\第二季_12_CAS\unsafe-getAndAddInt.png)

```
public final int getAndAddInt(Object var1, long var2, int var4) {
    int var5;
    do {
        var5 = this.getIntVolatile(var1, var2);
    } while (!this.compareAndSwapInt(var1, var2, var5, var4 + var5));
    return var5;
}
```
参数列表：  
- var1 AtomicInteger本身（this）
- var2 变量var1的内存地址
- var3 
- var4 值修改的量，比如加1的这个1
- var5 通过内存地址找出的当前时间保存在这个位置上的值

方法执行过程：  
取出地址上的值var5，并与var1比较（我感觉应该是var5和var2地址上的值比较，如果是var5和var1比较不久死循环了吗）  
如果一致，说明没有被修改（这里先不考虑ABA问题）。就可以将var4+var5的值写到这个地址上，compareAndSwapInt()方法返回true，结束do-while循环  
如果不一致，就继续执行do-while循环，这个循环称为自旋  
问题：如果不一致，在这个方法里不是死循环了？  
回答：不会，因为do-while的方法体里会读取一次var5，不可能一直死循环  

![CAS下两个线程修改同一个值可能的一种情况.png](http://ww1.sinaimg.cn/large/005IGVTXly1gbp0ct6xpwj31hc0u04qp.jpg)

底层汇编

unsafe.cpp

![unsafe汇编](E:\study\atguigu-interview-questions\src\main\java\第二季_12_CAS\unsafe汇编.png)

# 为什么用CAS而不用synchronized

synchronized加锁，一致性得到保障，但是并发性下降，CAS既保证了一致性又保证了并发性  

# CAS的缺点
- 比较失败会一直尝试，如果长时间不成功，可能会给CPU带来很大的开销  
- 只能保证一个共享变量的原子性，注意是一个。多个变量时就不能保证原子性了
- ABA问题