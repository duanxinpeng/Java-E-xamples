package concurrent;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class ThreadTest extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
    }
}
public class ThreadCreate {
    public static void main(String[] args) {
        //1. 继承Thread类
        ThreadTest tt = new ThreadTest();
        tt.start();
        tt.start();

        //2. Runnable 接口+Thread
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }).start();
        //3. Callable接口 + FutureTask + Thread;
        FutureTask<String> ft = new FutureTask<>(()->{
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
            Thread.sleep(10000);
            return "duanxinpeng";
        });
        new Thread(ft).start();
        try {
            //会阻塞！！
            System.out.println("线程返回值："+ft.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("zusefou?");
    }
}
