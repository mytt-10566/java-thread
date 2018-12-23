package com.momo.thread.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class Demo {

    // AtomicInteger AtomicLong AtomicIntegerArray 等
    // 内部CAS（compare and swap）机制实现
    @Test
    public void testAtomicLong() {
        AtomicInteger atomicInteger = new AtomicInteger();
        int i = atomicInteger.incrementAndGet();
        System.out.println(i);

        int observed = 5;
        int oldValue = atomicInteger.get();
        atomicInteger.updateAndGet(x -> Math.max(x, observed));
        // or
        atomicInteger.accumulateAndGet(observed, Math::max);
    }

    @Test
    public void testLongAdder() {
        LongAdder longAdder = new LongAdder();

    }

    @Test
    public void testLongAccumulator() {
        long value = 1;
        LongAccumulator adder = new LongAccumulator(Long::sum, 0);
        adder.accumulate(value);

    }
}
