package com.momo.thread.synch.reentrant;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock
 * */
public class Bank {
    // 构建可以被用来保护临界区的可重入锁，默认非公平锁
    private Lock bankLock = new ReentrantLock();
    private final double[] accounts;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    public void transfer(int from, int to, double amount) {
        // 获取锁，如果锁同时被另一个线程拥有则发生阻塞
        bankLock.lock();

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
            bankLock.unlock();
        }

    }

    public double getTotalBalance() {
        double sum = 0;
        for (double a : accounts) {
            sum += a;
        }
        return sum;
    }

    public int size() {
        return accounts.length;
    }

}
