package com.momo.thread.synch.readwrite;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用ReentrantLock
 * */
public class Bank {
    // 构建可以被用来保护临界区的读写锁
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    // 得到一个可以被多个读操作共用的读锁，但会排斥所有写操作
    private Lock readLock = rwLock.readLock();
    // 得到一个写锁，排斥所有其他的读操作和写操作
    private Lock writeLock = rwLock.writeLock();

    private final double[] accounts;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    public void transfer(int from, int to, double amount) {
        // 获取锁，如果锁同时被另一个线程拥有则发生阻塞
        writeLock.lock();

        try {
            if (accounts[from] < amount)
                return;
            System.out.println(Thread.currentThread());

            accounts[from] -= amount;
            System.out.printf("%10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf("Total Balance: %10.2f%n", getTotalBalance());
        } finally {
            // 释放锁
            writeLock.unlock();
        }

    }

    public double getTotalBalance() {
        readLock.lock();

        try {
            double sum = 0;
            for (double a : accounts) {
                sum += a;
            }
            return sum;
        } finally {
            readLock.unlock();
        }
    }

    public int size() {
        return accounts.length;
    }

}
