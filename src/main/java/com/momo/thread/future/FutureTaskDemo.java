package com.momo.thread.future;

public class FutureTaskDemo {

    /**
     * FutureTask
     *  内部基于AQS实现，
     *
     * FutureTask的3种状态：
     *      未启动：FutureTask.run()方法还没有被执行之前
     *      已启动：FutureTask.run()方法被执行的过程中
     *      已完成：FutureTask.run()方法执行完后正常结束，或被取消，或执行FutureTask.run()方法时抛出异常而异常结束，此时FutureTask处于已完成状态
     *
     * FutureTask的使用
     *      可以把FutureTask交给Executor执行
     *      可以通过ExecutorService.submit()方法返回一个FutureTask，然后执行FutureTask.cancel()方法
     *      直接使用FutureTask
     *
     * */
}
