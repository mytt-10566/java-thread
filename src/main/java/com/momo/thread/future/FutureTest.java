package com.momo.thread.future;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTest {

    public static void main(String[] args) {
        String directory = "D:/java/jdk/jdk1.8.0_181/src";
        String keyword = "volatile";

        MatchCounter counter = new MatchCounter(new File(directory), keyword);
        FutureTask<Integer> task = new FutureTask<>(counter);
        Thread thread = new Thread(task);
        thread.start();

        try {
            System.out.println(task.get() + " matching files.");

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {

        }
    }
}

class MatchCounter implements Callable<Integer> {
    private File directory;
    private String keyword;

    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    @Override
    public Integer call() {
        int count = 0;
        try {
            File files[] = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword);
                    FutureTask<Integer> task = new FutureTask<>(counter);
                    results.add(task);
                    Thread thread = new Thread(task);
                    thread.start();
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
//                    System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
