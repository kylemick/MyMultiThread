package com.ck.future;

import java.util.concurrent.*;

/**
 * Created by ? on 2015/4/27.
 */
public class MyThreadPool extends ThreadPoolExecutor {
    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public Future<?> submit(Runnable task) {
        if (task == null) throw new NullPointerException();
        FutureCall<Object> ftask = new FutureCall<Object>(task, null);
        execute(ftask);
        return ftask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        if (task == null) throw new NullPointerException();
        FutureCall<T> ftask = new FutureCall<T>(task, result);
        execute(ftask);
        return ftask;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        FutureCall<T> ftask = new FutureCall<T>(task);
        execute(ftask);
        return ftask;
    }

    static class FutureCall<T> extends FutureTask<T> implements Comparable<FutureCall> {
        private Integer priority;

        public FutureCall(Callable callable) {
            super(callable);
            if (callable instanceof MyCallable) {
                this.priority=((MyCallable) callable).getPriority();
            }
        }

        public FutureCall(Runnable runnable, Object result) {
            super(runnable, (T) result);
        }

        public int compareTo(FutureCall o) {
            return priority.compareTo(o.priority);
        }
    }
}
