package com.ck.executor;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ? on 2015/4/27.
 */
public class TimingThreadPool extends ThreadPoolExecutor {
    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final Logger log = Logger.getLogger("com.ck.executor.TimingThreadPool");
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.severe(String.format("Thread %s : start %s ", t, r));
        startTime.set(System.currentTimeMillis());
//        throw new RuntimeException();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {

        long endTime = System.currentTimeMillis();
        try {
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            log.severe(String.format("Thread %s: end %s , time=%dns", t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {

        try {
            log.severe(String.format("Terminated: avg time =%dns", totalTime.get() / numTasks.get()));
        } finally {
            super.terminated();
        }
    }

    public static void main(String[] args) {

        BlockingQueue workQueue = new LinkedBlockingQueue(100);
        TimingThreadPool pool = new TimingThreadPool(5, 10, 0L, TimeUnit.SECONDS,workQueue );
//        Logger log = Logger.getLogger("TimingThreadPool");
//        log.setLevel(Level.FINEST);
        for (int i = 0; i < 100; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(500));
//                        System.out.println("thread running ....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pool.shutdown();

    }
}
