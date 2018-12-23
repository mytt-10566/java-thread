package com.momo.thread;

import org.junit.Test;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @author: MQG
 * @date: 2018/12/19
 */
public class JVMTest {
    
    @Test
    public void test01() {
        List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean bean : beans) {
            System.out.println(bean.getName());
        }
    }
}
