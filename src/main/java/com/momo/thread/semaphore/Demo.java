package com.momo.thread.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @author: MQG
 * @date: 2018/12/17
 */
public class Demo {

    public static void main(String[] args) {
        test1();
    }

    /**
     * Semaphore(信号量)实际上就是可以控制同时访问的线程个数，它维护了一组"许可证"。
     * 当调用acquire()方法时，会消费一个许可证。如果没有许可证了，会阻塞起来
     * 当调用release()方法时，会添加一个许可证。
     * 这些"许可证"的个数其实就是一个count变量罢了
     */
    public static void test1() {
        int nums = 50;
        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < nums; i++) {
            new Thread(() -> {
                try {
                    // 获取许可
                    semaphore.acquire();

                    String name = Thread.currentThread().getName();
                    System.out.println(name + "购买东西");
                    Thread.sleep(1000);

                    // 购买结束，归还许可
                    System.out.println(name + "购买结束");
                    semaphore.release();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
