package procon.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    //private static Integer count = 0;
    final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
    //final BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<>(10);
    //Lock lock = new ReentrantLock();
    class Producer implements Runnable {
        @Override
        public void run() {
            for (;;) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    blockingQueue.put(1);
                    //lock.lock();
                    System.out.println("生产者"+Thread.currentThread()+"生产");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            for (; ; ) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    blockingQueue.take();
                    //lock.lock();
                    System.out.println("消费者:" + Thread.currentThread() + "消费");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        Thread t1 = new Thread(main.new Producer());
        t1.start();
        Thread t2 = new Thread(main.new Consumer());
        t2.start();
    }
}
