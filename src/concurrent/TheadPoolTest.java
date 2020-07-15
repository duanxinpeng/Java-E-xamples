package concurrent;

import java.util.concurrent.*;

public class TheadPoolTest {
    /**
     * 线程池工作流程
     *      1、提交一个任务，线程池里存活的核心线程数小于线程数corePoolSize时，线程池会创建一个核心线程去处理提交的任务。
     *      2、如果线程池核心线程数已满，即线程数已经等于corePoolSize，一个新提交的任务，会被放进任务队列workQueue排队等待执行。
     *      3、当线程池里面存活的线程数已经等于corePoolSize了,并且任务队列workQueue也满，判断线程数是否达到maximumPoolSize，即最大线程数是否已满，如果没到达，创建一个非核心线程执行提交的任务。
     *      4、如果当前的线程数达到了maximumPoolSize，还有新的任务过来的话，直接采用拒绝策略处理。
     *
     */
    public void testCacheThreadPool() {
        /**
         * 返回ThreadPoolExecutor实例
         *      corePoolSize = 0
         *      maximumPoolSize = Integer.MAX_VALUE
         *      keepAliveTime = 60L
         *      unit = TimeUnit.SECONDS
         *      workQueue = SynchronousQueue(同步队列)
         *  当有新任务到来，则插入SynchronousQueue中，在池中寻找可用线程来执行
         *  若有线程则执行，
         *  若没有可用线程，则创建一个线程来执行该任务；
         *  若池中线程的空闲时间超过指定大小，则该线程会被销毁。
         *  适用于执行很多短期异步的小程序或者负载较轻的服务器。
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int ii = i;
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名称:"+Thread.currentThread().getName()+"执行:"+ii);
                }
            });
        }
        cachedThreadPool.shutdown();
        while (true) {
            try {
                if (cachedThreadPool.awaitTermination(1, TimeUnit.SECONDS)) break;
                System.out.println("continuing");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testNewFixedThreadPool() {
        /**
         *返回ThreadPoolExecutor实例，参数为：nThread
         *      corePoolSize = nThread；
         *      maximumPoolSize = nThread；
         *      keepAliveTime = 0（不限时）；
         *      unit = TimeUnit.MILLISECONDS
         *      workqueue = LinkedBlockingQueue（无界阻塞队列）
         * 创建可容纳固定数量线程的池子，每个线程的存活时间无限，当池子满了就不再添加线程了；、、
         * 如果池中所有线程均在繁忙状态，对于新任务会进入阻塞队列（无界阻塞队列）
         * 适用于执行长期的任务
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int ii = i;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fixedThreadPool.execute(() -> {
                System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行" + ii);
            });
        }
        fixedThreadPool.shutdown();
        while (true) {
            try {
                if (fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS)) break;
                System.out.println("continuing");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testSingleThreadPool() {
        /**
         * 返回FinalizableDelegatedExecutorService包装的ThreadPoolExecutor实例
         *      corePoolSize = 1
         *      maximumPoolSize = 1
         *      keepAliveTime = 0L
         *      unit = TimeUnit.MILLISECONDS
         *      workQueue = LinkedBlockingQueue（无界阻塞队列）
         *  创建只有一个线程的线程池，线程存活时间无限制；
         *  当该线程繁忙时，新任务会进入阻塞队列中
         */
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int ii = i;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pool.execute(() -> System.out.println(Thread.currentThread().getName() + "=>" + ii));
        }
    }

    public void testScheduledThreadPool() {
        /**
         * 创建ScheduledThreadPoolExecutor实例
         *      corePoolSize = 参数
         *      maximumPoolSize = Integer.MAX_VALUE
         *      keepAliveTime = 0
         *      unit = TimeUnit.NANOSECONDS
         *      workQueue = DelayedWorkQueue（按照超时时间升序排序的队列）
         * 创建一个固定大小的线程池，线程存活时间无限，支持定时及周期性任务执行；
         * 如果所有线程均处于繁忙状态，新任务会进入DelayedWorkQueue队列中；
         */
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        Runnable r1 = ()-> System.out.println(Thread.currentThread().getName()+"=>"+"3秒后执行");
        scheduledExecutorService.schedule(r1,3, TimeUnit.SECONDS);
        Runnable r2 = ()-> System.out.println(Thread.currentThread().getName()+"=>"+"2秒后执行每3秒执行一次");
        scheduledExecutorService.scheduleAtFixedRate(r2,2,3,TimeUnit.SECONDS);
        Runnable r3 = ()-> System.out.println(Thread.currentThread().getName()+"=>"+"普通任务");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scheduledExecutorService.execute(r3);
        }
        ConcurrentHashMap<String, String> cm = new ConcurrentHashMap<>();
    }

    public static void main(String[] args) {
        TheadPoolTest tpt = new TheadPoolTest();
        tpt.testCacheThreadPool();
    }
}
