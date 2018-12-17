package com.momo.thread.init;

import org.junit.Test;

import java.util.concurrent.*;

public class Demo {

    // 方式一，继承Thread，重写run方法
    class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
        }
    }

    @Test
    public void thread() {
        new MyThread().start();
    }

    // 方式二 实现Runnable接口
    @Test
    public void runnable() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
        }).start();
    }

    // 方式三
    @Test
    public void futureTask() {
        Callable<String> callable = () -> {
            Thread.sleep(2000);
            System.out.println("sub thread task finish");
            return "run";
        };
        FutureTask<String> future = new FutureTask<>(callable);
        new Thread(future).start();

        try {
            // 主线程处理其他任务
            System.out.println("main thread task begin");
            Thread.sleep(3000);
            System.out.println("main thread task end");
            // 子线程完成返回
            System.out.println("sub thread return value: " + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 方式四
    @Test
    public void executorService() {
        Callable<String> callable = () -> {
            Thread.sleep(2000);
            System.out.println("sub thread task finish");
            return "run";
        };

        // 只有一个线程的“池”，该线程顺序执行每一个提交的任务（类似于Swing事件分配线程）
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<String> future = threadPool.submit(callable);

        try {
            // 主线程处理其他任务
            System.out.println("main thread task begin");
            Thread.sleep(3000);
            System.out.println("main thread task end");
            // 子线程完成返回
            System.out.println("sub thread return value: " + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initThreadPool() {
        // 执行器（Executor）类的静态方法用来构造线程池

        // 必要时创建新线程，空闲线程会被保留60s
        ExecutorService threadPool1 = Executors.newCachedThreadPool();

        // 该池包含固定数量的线程，空闲线程会被一直保留
        ExecutorService threadPool2 = Executors.newFixedThreadPool(16);

        // 只有一个线程的“池”，该线程顺序执行每一个提交的任务（类似于Swing事件分配线程）
        ExecutorService threadPool3 = Executors.newSingleThreadExecutor();

        // 用于预定执行而构建的固定线程池，替代java.util.Timer
        ExecutorService threadPool4 = Executors.newScheduledThreadPool(16);

        // 用于预定执行而构建的单线程“池”
        ExecutorService threadPool5 = Executors.newSingleThreadScheduledExecutor();
    }

    private void submit() {
        ExecutorService threadPool = Executors.newFixedThreadPool(16);

        // 返回Future<?>，可以用来调用isDone、cancel或isCanceled。get方法在完成的时候只是简单地返回null
        Future<?> future1 = threadPool.submit(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
            }
        });

        // Future的get方法在完成的时候返回指定的result对象
//        Future<Integer> future2 = threadPool.submit(() -> {
//        }, Integer.class);

        // 提交一个Callable，并且返回的Future对象将在计算结果准备好的时候得到它
        Future<Integer> future3 = threadPool.submit(() -> {
            return 1;
        });
    }

}
