package com.momo.thread.threadPool;

public class ExecutorFrame {

    /**
     * Executor框架
     *
     * ThreadPoolExecutor
     *  通常使用Executors工厂类来创建，可以创建以下3种类型的ThreadPoolExecutor：
     *      FixedThreadPool 适用于为了满足资源管理的需求，需要限制当前线程数量的应用场景，适用于负载比较重的服务器
     *      SingleThreadPool 适用于需要保证顺序地执行各个人物；并且在任意时间点，不会有多个线程是活动的场景
     *      CachedThreadPool 适用于执行很多的短期异步任务的小程序，或负载较轻的服务器
     *
     * ScheduledThreadPoolExecutor
     *  通常使用Executors工厂类来创建，可以创建以下2种类型的ScheduledPoolExecutor：
     *      ScheduledThreadPoolExecutor 适用于需要多个后台线程执行周期任务，同时为了满足资源管理的需求而需要限制后台线程的数量的应用场景
     *      SingleThreadScheduledExecutor 适用于需要单个后台线程执行周期任务，同时需要保证顺序地执行各个任务的应用场景
     *
     * Future接口
     *  Future接口和实现该接口的FutureTask类用来表示异步任务计算的结果。
     *
     * Runnable和Callable接口
     *
     *  将Runnable包装成Callable接口的API：
     *      static Callable<Object> callable(Runnable task)
     *      static <T> Callable<T> callable(Runnable task, T result)
     * */
}
