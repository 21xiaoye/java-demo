package com.example.concurrent.queue.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberConsumer implements Runnable{
    private final BlockingQueue<Integer> blockingQueue;
    private final int poisonPill;
    public static AtomicInteger THREAD_COUNT = new AtomicInteger(0);

    public NumberConsumer(BlockingQueue<Integer> blockingQueue, int poisonPill) {
        this.blockingQueue = blockingQueue;
        this.poisonPill = poisonPill;
    }


    @Override
    public void run() {
        while (true){
            try {
                Integer take = blockingQueue.take();
                if (take.equals(poisonPill)) {
                    return;
                }
                THREAD_COUNT.incrementAndGet();
                String result = take.toString();
                System.out.println(Thread.currentThread().getName() + " result: " + result);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
