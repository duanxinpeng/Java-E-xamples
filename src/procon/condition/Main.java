package procon.condition;

import java.util.Map;
import java.util.concurrent.locks.*;

public class Main {
    int count = 0;
    int full = 10;
    Lock lock = new ReentrantLock();
    //ReadWriteLock lock = new ReentrantReadWriteLock();
    //这里是否需要两个条件变量来实现？
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();

    class Producer implements Runnable {

        @Override
        public void run() {
            for (;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    lock.lock();
                    if (count == 10) {
                        try {
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println("生产者:" + Thread.currentThread() + "生产");
                    notEmpty.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            for (;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    lock.lock();
                    if (count == 0) {
                        try {
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println("消费者"+Thread.currentThread()+"消费");
                    notFull.signal();
                } finally {
                    lock.unlock();
                }

            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        for (int i = 0; i < 5; i++) {
            new Thread(main.new Producer()).start();
        }
        for (int i = 0; i < 6; i++) {
            new Thread(main.new Consumer()).start();
        }
    }
}
