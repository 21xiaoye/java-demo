package com.example.concurrent.queue.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberProducer implements Runnable{
    private final BlockingQueue<Integer> numberQueue;
    private final int poisonPill;
    private final int poisonPillPerproducer;
    public static AtomicInteger THREAD_COUNT = new AtomicInteger(0);
    private int count =0;
    public NumberProducer(BlockingQueue<Integer> numberQueue, int poisonPill, int poisonPillPerproducer) {
        this.numberQueue = numberQueue;
        this.poisonPill = poisonPill;
        this.poisonPillPerproducer = poisonPillPerproducer;
    }

    @Override
    public void run() {
        try {
            generateNumbers();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

    }
    private void generateNumbers()throws InterruptedException{
        for (int i = 0; i < 100; i++) {
            THREAD_COUNT.incrementAndGet();
            numberQueue.put(ThreadLocalRandom.current().nextInt());
        }

        for (int i = 0; i < poisonPillPerproducer; i++) {
            numberQueue.put(poisonPill);
        }
    }
}

































