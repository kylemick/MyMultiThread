package com.ck.unit;

import java.util.concurrent.Semaphore;

/**
 * Created by ٩ on 2015/6/23.
 */

public class BoundedBuffer<E> {
    private final Semaphore availabelItems, availabelSpace;
    private final E[] items;
    private int putPosition=0, takePosition = 0;

    public BoundedBuffer(int capacity) {
        availabelItems = new Semaphore(0);
        availabelSpace = new Semaphore(capacity);
        items=(E[])new Object[capacity];
    }

    public boolean isEmpty() {
        return availabelItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availabelSpace.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException {
        availabelSpace.acquire();
        doInsert(x);
        availabelItems.release();
    }
    public E take() throws InterruptedException{
        availabelItems.acquire();
        E item = doExtract();
        availabelSpace.release();
        return item;
    }

    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i==items.length)?0:i;
    }

    private synchronized E doExtract() {
        int i = takePosition;
        E x = items[i];
        items[i]=null;
        takePosition = (++i == items.length) ? 0 : i;
        return x;
    }
}
