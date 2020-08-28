package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        new Thread(()->{
            try {
                Thread.sleep(1000);
                lock.lock();

                System.out.println("wait");
                condition.await();
                System.out.println("waitjieshu");
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            lock.lock();
            System.out.println("signal");
            condition.signal();
            System.out.println("signaljieshu");
            lock.unlock();
        }).start();
    }
}
