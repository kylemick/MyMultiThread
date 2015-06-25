package com.ck.future;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by ? on 2015/4/28.
 */
public class MyCallable<Object> implements Callable,Comparable<MyCallable> {

    private Integer priority;

    private Object object;

    public MyCallable(Object object) {

        this.object = object;
        this.priority = new Random().nextInt(5);
    }

    public Object call() throws Exception {
        Thread.sleep(new Random().nextInt(5000));
        return object;
    }

    public int compareTo(MyCallable o) {
        return priority.compareTo(o.priority);
    }

    public Integer getPriority() {
        return priority;
    }
}
