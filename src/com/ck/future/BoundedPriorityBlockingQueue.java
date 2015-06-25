package com.ck.future;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ? on 2015/4/28.
 */
public class BoundedPriorityBlockingQueue extends PriorityBlockingQueue {

    private final Semaphore semaphore;

    public BoundedPriorityBlockingQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
        semaphore = new Semaphore(capacity);
    }

    @Override
    public boolean offer(Object o) {
        if (o == null) throw new NullPointerException();
//        System.out.println("this.size() = " + this.size());
        if (!semaphore.tryAcquire()) {
            return false;
        }
        boolean wasAdded = false;

        try {
            wasAdded = super.offer(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                semaphore.release();
            }
        }
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public Object poll() {
        Object obj = super.poll();
        semaphore.release();
//        System.out.println("poll this.size() = " + this.size());
        return obj;
    }

//    @Override
//    public Object take() throws InterruptedException {
//        return super.take();
//    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public Object[] toArray() {
        return super.toArray();
    }

    @Override
    public int remainingCapacity() {
        return semaphore.availablePermits();
    }
}
