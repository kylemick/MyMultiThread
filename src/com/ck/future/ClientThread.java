package com.ck.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by ? on 2015/4/28.
 */
public class ClientThread extends Thread {
    private ThreadPoolExecutor executor;

    public ClientThread(ThreadPoolExecutor executor) {
        this.executor=executor;
    }


    public void run() {
        Future future=null;
        try {
            future=executor.submit(new MyCallable(Thread.currentThread().getName()));
//            Object o = future.get();
            Object o = future.get(5, TimeUnit.SECONDS);
            System.out.println("o = " + o);
        }
        catch (Exception e) {
            e.printStackTrace();
            future.cancel(true);
        }
//        catch (InterruptedException e) {
//            future.cancel(true);
//
//        } catch (ExecutionException e) {
////            future.cancel(true);
//        } catch (TimeoutException e) {
////            future.cancel(true);
//        }
    }
}
