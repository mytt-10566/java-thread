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

}
