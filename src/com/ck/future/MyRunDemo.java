package com.ck.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by ٩ on 2015/4/28.
 */
public class MyRunDemo {
    public static void main(String[] args) {
        BoundedPriorityBlockingQueue workQueue = new BoundedPriorityBlockingQueue(100);
        MyThreadPool pool = new MyThreadPool(5, 5, 0L, TimeUnit.SECONDS, workQueue);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (int i = 0; i < 1000; i++) {
            ClientThread t = new ClientThread(pool);
            t.start();
        }

        pool.shutdown();
    }
}
