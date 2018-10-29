package com.momo.thread.threadPool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {
        String directory = "D:/java/jdk/jdk1.8.0_181/src";
        String keyword = "volatile";

        // 必要时创建新线程，空闲线程会被保留60s
        ExecutorService pool = Executors.newCachedThreadPool();
        MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
        Future<Integer> result = pool.submit(counter);
        try {
            System.out.println(result.get() + " matching files.");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {

        }
        // 启动该池的关闭序列，被关闭的执行器不再接受新的任务。当所有任务的完成以后，线程池中的线程死亡
        pool.shutdown();
        // 取消尚未开始的所有任务并试图中断正在运行的线程
        // pool.shutdownNow();

        int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largest pool size = " + largestPoolSize);
    }
}

class MatchCounter implements Callable<Integer> {
    private File directory;
    private String keyword;
    private ExecutorService pool;
    private int count;

    public MatchCounter(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() {
        count = 0;
        try {
            File files[] = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword, pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);
                } else {
                    if (search(file)) {
                        count++;
                    }
                }
            }

            for (Future<Integer> result : results) {
                try {
                    count += result.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
        }
        return count;
    }

    public boolean search(File file) {
        try {
            Scanner scanner = new Scanner(file, "UTF-8");
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains(keyword)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
