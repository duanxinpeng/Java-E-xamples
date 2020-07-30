package concurrent;

public class SynchronizedTest {
    public static void main(String[] args) throws InterruptedException {
        String lockA = "A";
        String lockB = "B";
        new Thread(()->{
            synchronized (lockA) {
                System.out.println("Thread1:lockA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockB) {
                    System.out.println("Thread1:lockB");
                }
            }
        }).start();
        new Thread(()->{
            synchronized (lockB) {
                System.out.println("Thread2:lockB");
                synchronized (lockA) {
                    System.out.println("thread2:lockA");
                }
            }
        }).start();
    }
}
