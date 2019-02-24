package com.momo.thread.concurrentutil.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author: MQG
 * @date: 2018/12/17
 */
public class Demo {

    /**
     * CountDownLatch允许一个或多个线程等待其他线程完成操作。
     *  类似于线程的join()方法
     * */

    public static void main(String[] args) {
//        test01();
        test02();
    }

    /**
     * 5个员工xxx下班了，mo才允许下班
     */
    public static void test01() {
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        System.out.println("6点下班了。。。");

        // mo线程启动
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("mo go home");
        }).start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("employee xxx go home");
                countDownLatch.countDown();
            }).start();
        }
    }

    /**
     * mo做事慢，所有人需要等到mo做完后才可以继续
     */
    public static void test02() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        // mo线程
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("mo finish");
            countDownLatch.countDown();
        }).start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("employ xxx wait mo");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("mo finish, employ xxx start");
            }).start();
        }
    }
}
