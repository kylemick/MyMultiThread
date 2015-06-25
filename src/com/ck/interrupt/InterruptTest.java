package com.ck.interrupt;

/**
 * Created by ٩ on 2015/5/2.
 */
public class InterruptTest {
    public static void main(String[] args) {
        Thread t = new Thread(new RunningThread());
        t.start();

        t.interrupt();
//        Thread.interrupted();
    }

}
