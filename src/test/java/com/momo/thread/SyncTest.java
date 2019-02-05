package com.momo.thread;

public class SyncTest {

    static SyncTest a = new SyncTest();
    static SyncTest b = new SyncTest();

    public static void main(String[] args) {
        new Thread(() -> {
            syncBlock();
        }, "A").start();

        new Thread(() -> {
            method();
        }, "B").start();
    }

    public static void syncBlock() {
        synchronized (a) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void method() {
        synchronized (b) {
            System.out.println("enter sync method");
        }
    }
}
