package com.momo.thread.threadlocal;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Demo {

    @Test
    public void testDateFormat() {
        // SimpleDateFormat线程不安全
        ThreadLocal<SimpleDateFormat> dateFormatThreadLocal =
                ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
        String strDate = dateFormatThreadLocal.get().format(new Date());
        System.out.println(strDate);
    }

    @Test
    public void testRandom() {
        // 返回特定于当前线程的Random类实例
        int num = ThreadLocalRandom.current().nextInt(10);
    }
}
