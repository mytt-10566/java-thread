package com.momo.thread;

import org.junit.Test;

public class ConcurrentTest {
    @Test
    public void testRun() {
        for (int i = 0; i < 10000; i++) {
            Runnable runner = () -> {
                try {
                    run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(runner, "thread-" + i).start();
        }
    }

    public void run() throws InterruptedException {
        int num = 0;
        for (int i = 0; i < 50; i++) {
            num = num + 1;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
        }
    }
}
