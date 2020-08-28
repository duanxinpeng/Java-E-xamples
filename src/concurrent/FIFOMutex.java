package concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class FIFOMutex {
    // 用LockSupport实现锁！
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();
    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);
        // 只有自己在队首，才可以获得锁，否则阻塞
        // cas 操作失败，阻塞
        //
        while (waiters.peek() != current || !locked.compareAndExchange(false,true)) {
            //使用 LockSupport 阻塞当前线程
            LockSupport.park(this);
            // 忽略线程中断，只是记录下曾经被中断过
            // java 中的中断仅仅是一个状态，要不要退出程序或者抛出异常，需要程序员来控制！
            if (Thread.interrupted()) {
                wasInterrupted = true;
            }
        }
        //成功获得锁，将 locked 置为 true，将自己移出队列！
        waiters.remove();
        if (wasInterrupted) {
            current.interrupt();
        }
    }
    public void unlock() {
        locked.set(false);
        // 唤醒队列头元素
        LockSupport.unpark(waiters.peek());
    }
}
