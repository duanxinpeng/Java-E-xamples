package procon.semaphore;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Main {
    int count = 0;
    final Semaphore notFull = new Semaphore(10);
    final Semaphore notEmpty = new Semaphore(0);
    final Semaphore mutex = new Semaphore(1);

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
                    notFull.acquire();
                    mutex.acquire();
                    System.out.println(Thread.currentThread().getName() + "生产者者生产，目前总共有" + count);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notEmpty.release();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    notEmpty.acquire();
                    mutex.acquire();
                    count--;
                    System.out.println(Thread.currentThread().getName()
                            + "消费者消费，目前总共有" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notFull.release();
                }
            }
        }
    }
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        Main test4 = new Main();
        new Thread(test4.new Producer()).start();

        new Thread(test4.new Consumer()).start();
        new Thread(test4.new Producer()).start();
        new Thread(test4.new Consumer()).start();
        new Thread(test4.new Producer()).start();
        new Thread(test4.new Consumer()).start();
        new Thread(test4.new Producer()).start();
        new Thread(test4.new Consumer()).start();
    }
}
