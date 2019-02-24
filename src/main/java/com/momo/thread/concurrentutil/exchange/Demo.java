package com.momo.thread.concurrentutil.exchange;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {

    /**
     * Exchanger（交换者）是一个用于线程间协作的工具类。Exchanger用于线程间的数据交换。
     *  它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。
     *  这两个线程通过exchange交换数据，如果第一个线程先执行exchange方法，它会一直等待第二个线程也执行exchange方法，
     *  当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方。
     *
     * 应用场景
     *  遗传算法
     *      遗传算法里需要选出两个人作为交配对象，这时候会交换两人的数据，并使用交叉规则得出2个交配结果。
     *  校对工作
     *      比如我们需要将纸质银行流水通过人工的方式录入成电子银行流水，为了避免错误，采用AB岗两人进行录入，录入到Excel后，系统加载这两个Excel，并对两个Excel数据进行校对，查看是否录入一致。
     * */

    private static final Exchanger<String> exchanger = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(() -> {
            try {
                String A  = "银行流水A";
                String B = exchanger.exchange(A);

                System.out.println("A交换完成，B" + B);
            } catch (Exception e) {

            }
        });

        threadPool.execute(() -> {

            try {
                String B = "银行流水B";

                String A = exchanger.exchange(B);

                System.out.println("A和B数据是否一致：" + A.equals(B) + "，A录入的内容是：" + A + "，B录入的内容是：" + B);
            } catch (Exception e) {

            }

        });
        threadPool.shutdown();
    }


}
