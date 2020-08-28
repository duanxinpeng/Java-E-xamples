package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    public static void main(String[] args) {
        final List<Thread> list = new ArrayList<>();
        new Thread(()->{
            list.add(Thread.currentThread());
            System.out.println("add");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park();
            System.out.println("park1");
            LockSupport.park();
            System.out.println("park2");
        }).start();
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.unpark(list.get(0));
            System.out.println("unpark1");
            LockSupport.unpark(list.get(0));
            System.out.println("unpark2");
        }).start();
    }
}
