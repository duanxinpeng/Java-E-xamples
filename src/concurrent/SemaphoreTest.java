package concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class SemaphoreTest {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(0);

        new Thread(()-> {
            try {
                Thread.sleep(1000);
                synchronized (semaphore) {
                    semaphore.wait();
                }
                semaphore.acquire();
                System.out.println("acqure1");
                semaphore.acquire();
                System.out.println("acqure2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            synchronized (semaphore) {
                semaphore.notify();
            }
            semaphore.release();
            semaphore.release();
            System.out.println("release");
        }).start();
    }
}
