package com.ck;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by 侃 on 2015/4/27.
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                queue.put(p=p.nextProbablePrime());
                System.out.println("p = " + p);
            } catch (InterruptedException e) {
//                e.printStackTrace();
                System.out.println("寻找任务取消");
                Thread.currentThread().interrupt();//清除中断状态
            }
        }
        System.out.println("执行结束");
        int N_CPUS=Runtime.getRuntime().availableProcessors();
        System.out.println("N_CPUS = " + N_CPUS);
    }
    public void cancel() {
        interrupt();
    }

    public static void main(String[] args) {
        BlockingQueue<BigInteger> queue = new LinkedBlockingQueue<BigInteger>();
        PrimeProducer t=new PrimeProducer(queue);
        t.start();
        try {
            Thread.sleep(100);
            t.cancel();
            System.out.println("queue = " + queue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }
}

