package com.ck.interrupt;

/**
 * Created by ٩ on 2015/5/2.
 */
public class RunningThread implements Runnable {
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        for (int i =0; ;i++ ) {
            if (i % 1000 == 0) {
                System.out.println("running");
            }
        }
    }
}
