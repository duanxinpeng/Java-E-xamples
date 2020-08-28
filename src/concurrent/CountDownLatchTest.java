package concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    /**
     * 1. 主线程执行完线程启动之后，子线程才能开始执行
     * 2. 子线程全部执行完之后，主线程才能继续下面的执行
     * @param args
     */
    public static void main(String[] args) {
        class Worker implements Runnable{
            private final CountDownLatch start;
            private final CountDownLatch end;

            public Worker(CountDownLatch start, CountDownLatch end) {
                this.start = start;
                this.end = end;
            }

            @Override
            public void run() {
                try {
                    start.await();
                    System.out.println("Start Working now ...");
                    end.countDown();//signal()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);

        System.out.println("Initializing ...");
        for (int i = 0; i < 5; i++) {
            new Thread(new Worker(start,end)).start();
        }
        start.countDown();
        System.out.println("Initialization Finished!");
        System.out.println("Workers working ...");
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished!");
    }
}
