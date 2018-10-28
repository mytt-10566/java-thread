package com.momo.thread.synch.condition;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock
 * */
public class Bank {
    // 构建可以被用来保护临界区的可重入锁
    private Lock bankLock = new ReentrantLock();
    // 条件变量，返回一个与该锁相关的条件对象
    private Condition sufficientFunds = bankLock.newCondition();
    private final double[] accounts;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    public void transfer(int from, int to, double amount) throws InterruptedException {
        // 获取锁，如果锁同时被另一个线程拥有则发生阻塞
        bankLock.lock();

        try {
            while (accounts[from] < amount) {
                // 将线程放到该条件的等待集中
                sufficientFunds.await();
            }
            System.out.println(Thread.currentThread());

            accounts[from] -= amount;
            System.out.printf("%10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf("Total Balance: %10.2f%n", getTotalBalance());

            // 从该条件的等待集中随机选择一个线程，解除其阻塞状态
            sufficientFunds.signalAll();
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
