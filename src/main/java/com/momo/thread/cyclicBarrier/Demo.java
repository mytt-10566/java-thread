package com.momo.thread.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: MQG
 * @date: 2018/12/17
 */
public class Demo {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * CyclicBarrier允许一组线程互相等待，直到到达某个公共屏障点。叫做cyclic是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用(对比于CountDownLatch是不能重用的)
     * CountDownLatch注重的是等待其他线程完成，CyclicBarrier注重的是：当线程到达某个状态后，暂停下来等待其他线程，所有线程均到达以后，继续执行。
     * */
    public static void test1() {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                String name = "";
                if (Thread.currentThread().getName().equals("Thread-0")) {
                    name = "mo";
                } else {
                    name = "lu";
                }
                System.out.println(name + " arrive shopping mall");
                
                try {
                    // 等待
                    cyclicBarrier.await();
                    System.out.println(name + " coming");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void test2() {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                String name = "";
                if (Thread.currentThread().getName().equals("Thread-0")) {
                    name = "mo";
                } else {
                    name = "lu";
                }
                System.out.println(name + " arrive shopping mall");

                try {
                    // 等待
                    cyclicBarrier.await();
                    System.out.println(name + " coming");
                    
                    // 等待
                    cyclicBarrier.await();
                    System.out.println(name + " eat");
                    
                    // 等待
                    cyclicBarrier.await();
                    System.out.println(name + " chat");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                
            }).start();
        }
    }
}
