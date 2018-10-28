package com.momo.thread.blockingQueue;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {

    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SEARCH_THREADS = 100;
    private static final File DUMMY = new File("");
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    @Test
    public void testGetUserDir() {
        System.getProperties().forEach((key, value) -> {
            System.out.println(key + "\t" + value);
        });
    }

    public static void main(String[] args) {
        String baseDir = "D:/java/jdk/jdk1.8.0_181/src";
        String keyword = "volatile";

        // 生产者
        Runnable enumerator = () -> {
            try {
                enumerate(new File(baseDir));
                queue.put(DUMMY);
            } catch (Exception e) {

            }
        };
        new Thread(enumerator).start();

        // 消费者
        for (int i = 0; i <= SEARCH_THREADS; i++) {
            Runnable searcher = () -> {
                try {
                    boolean done = false;
                    while (!done) {
                        File file = queue.take();
                        if (file == DUMMY) {
                            queue.put(file);
                            done = true;
                        } else {
                            search(file, keyword);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(searcher).start();
        }
    }

    public static void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                enumerate(file);
            } else {
                System.out.println("file info, path:\t" + file.getAbsolutePath());
                // 插入元素，队列满则阻塞
                queue.put(file);
            }
        }
    }

    public static void search(File file, String keyword) throws IOException {
        Scanner scanner = new Scanner(file, "UTF-8");
        int lineNumber = 0;
        while (scanner.hasNext()) {
            lineNumber++;
            String line = scanner.nextLine();
            if (line.contains(keyword)) {
                System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
            }
        }
    }
}
