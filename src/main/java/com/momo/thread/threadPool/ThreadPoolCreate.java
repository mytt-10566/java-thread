package com.momo.thread.threadPool;

import org.junit.Test;

import java.util.concurrent.*;

public class ThreadPoolCreate {

    /*
     * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)
     *
     * corePoolSize：核心线程池数，也就是即使线程池处于空闲状态，也要保持在池中的线程数
     * maximumPoolSize：池中允许的最大线程数
     * keepAliveTime：当线程数大于核心时，这是多余空闲线程在终止之前等待新任务的最长时间
     * unit：等待时间单位
     * workQueue：在执行任务之前用于保存任务的队列。 此队列仅包含execute方法提交的Runnable任务。
     *      ArrayBlockingQueue：基于数组结构的有界阻塞队列
     *      LinkedBlockingQueue：基于链表结构的无界阻塞队列
     *      SynchronousQueue：不村粗元素的无界阻塞队列
     *      PriorityBlockingQueue：具有优先级的无界阻塞队列
     * threadFactory：在执行程序创建新线程时使用的工厂，默认是DefaultThreadFactory
     *      DefaultThreadFactory： 每个新的线程创建为优先级设置为的非守护程序线程Thread.NORM_PRIORITY中较小的一个，以及线程组中允许的最大优先级。
     *      新线程的名称可通过Thread＃getName来访问pool-N-thread-M，其中N是该工厂的序列号，M是该工厂创建的线程的序列号。
     * handler：执行被阻塞时使用的处理器，因为达到了线程边界和队列容量，默认AbortPolicy
     *      AbortPolicy：直接抛出异常
     *      CallerRunsPolicy：只用调用者所在的线程运行任务
     *      DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务
     *      DiscardPolicy：不处理，丢弃掉
     *
     * 提交任务
     *   execute(Runnable);
     *   Future<V> future = submit(Callable);
     *
     * 关闭线程池
     *  shutdown()：关闭线程池，等待所有任务执行完
     *  shutdownNow()：关闭线程池，包括没有执行完的任务
     * */


    @Test
    public void createCustom() {
        // 1.创建
        // 基于链表的无界阻塞队列
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(5);
        // 核心线程数5，最大线程数10，空闲线程等待新任务时间为0（立即死亡），保存任务队列的初始容量为5
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, queue);

        // 2.提交任务
        // 2.1 方式一 提交不需要返回值得任务 Runnable
        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("task 1 finished");
        });
        // 2.2 方式二 提交需要返回值的任务
        Future<Integer> result = threadPoolExecutor.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("task 2 finished");
            return 1;
        });
        try {
            System.out.println("task 2 result is " + result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 3.关闭
        threadPoolExecutor.shutdown();
        //
        // threadPoolExecutor.shutdownNow();
    }

    // 核心线程和最大线程数为1的线程池
    @Test
    public void createSingle() {
        // 核心线程1 最大线程1 LinkedBlockingQueue（基于链表的无线阻塞队列） 饱和策略AbortPolicy（抛出异常）
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        System.out.println(executorService);
    }

    // 指定数量的线程池，核心线程和最大线程数一致
    @Test
    public void createFixed() {
        //  核心线程、最大线程5 LinkedBlockingQueue 饱和策略AbortPolicy（抛出异常）
        ExecutorService executorService = Executors.newFixedThreadPool(5);
    }

    @Test
    public void createCached() {
        // 核心线程数0 最大线程数Integer.MAX_VALUE SynchronousQueue（不存储元素的无界阻塞队列） 饱和策略AbortPolicy（抛出异常）
        ExecutorService executorService = Executors.newCachedThreadPool();
    }

}
