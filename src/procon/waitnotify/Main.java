package procon.waitnotify;

public class Main {
    private static Integer count = 0;
    private static int full = 10;
    private Integer lock = 1;
    
    class Producer implements Runnable {
        @Override
        public void run() {
            for (;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock) {
                    if (count == full) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println("生产者"+Thread.currentThread().getName()+"生产产品，共有产品："+count);

                    //随机唤醒等待线程中的一个
                    //lock.notify();
                    //唤醒所有等待线程
                    lock.notifyAll();
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
                synchronized (lock) {
                    if (count == 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println("消费者"+Thread.currentThread().getName()+"消费产品，剩余："+count);
                    lock.notifyAll();
                }
            }
        }
    }
    public void add() {
        for (int i = 0; i < 1000; i++) {
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        for (int i = 0; i < 5; i++) {
            new Thread(main.new Producer()).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(main.new Consumer()).start();
        }
    }
}
