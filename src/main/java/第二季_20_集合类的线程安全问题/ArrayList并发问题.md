> 视频P20到P23

# ArrayList类的java.util.ConcurrentModificationException
## 原因
多个线程同时修改list的内容，并发争抢导致的

参考花名册签名情况

![](E:\study\atguigu-interview-questions\src\main\java\第二季_20_集合类的线程安全问题\copyOnWrite.png)

## 解决方法
- 使用Vector类
- 使用Collections.synchronizedList()方法，参数为一个List对象
- 使用CopyOnWriteArrayList类   JUC



# CopyOnWriteArrayList类的add()方法源码
```
public boolean add(E e) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}
```

# 写时复制、读写分离思想

CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，而是先将当前object[]进行Copy，复制出一个新的容器Object[]newElements，然后新的容器Object[]newElements里添加元素，添加完元素之后，再将原容器的引用指向新的容setArray（newElements）;这样做的好处是可以对copyonwrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以copyonwrite容器也是一种读写分离的思想，读和写不同的容器。



# HashSet类也是线程不安全的

## 解决方法
- 使用Vector类
- 使用Collections.synchronizedSet()方法，参数为一个Set对象
- 使用CopyOnWriteArraySet类
> CopyOnWriteArraySet类的底层使用了CopyOnWriteArrayList类
>
> ```java
> public class CopyOnWriteArraySet<E> extends AbstractSet<E>
>         implements java.io.Serializable {
>     private static final long serialVersionUID = 5457747651344034263L;
> 
>     private final CopyOnWriteArrayList<E> al;
> 
>     /**
>      * Creates an empty set.
>      */
>     public CopyOnWriteArraySet() {
>         al = new CopyOnWriteArrayList<E>();
>     }
>     ...
> }
> ```
>
> HashSet底层是HashMap
>
> 
