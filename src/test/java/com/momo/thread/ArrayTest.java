package com.momo.thread;

/**
 *  参考：https://blog.csdn.net/jek123456/article/details/73087426
 * */
public class ArrayTest {
    String str = new String("tarena");
    char ch[] = {'a', 'b', 'c'};

    public static void main(String[] args) {
        ArrayTest ex = new ArrayTest();
        ex.change(ex.str, ex.ch);
        System.out.print(ex.str + " and ");
        System.out.print(ex.ch);
    }

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'g';
    }

}
