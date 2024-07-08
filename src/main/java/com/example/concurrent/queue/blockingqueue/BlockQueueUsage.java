package com.example.concurrent.queue.blockingqueue;

import com.example.concurrent.multithreading.BaeldungThreadFactory;

import java.util.concurrent.*;

public class BlockQueueUsage {
    private static final int BOUND = 10;
    private static final int N_PRODUCERS = 23;
    private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
    private static final int poisonPill = Integer.MAX_VALUE;
    private static final int poisonPillPerProducer  = N_CONSUMERS / N_PRODUCERS;
    private static final int mod = N_CONSUMERS % N_PRODUCERS;

    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(BOUND);
        BaeldungThreadFactory factory = new BaeldungThreadFactory("block");
        ExecutorService executorService = Executors.newFixedThreadPool(N_PRODUCERS+N_CONSUMERS, factory);
        for (int i = 0; i < N_PRODUCERS; i++) {
            executorService.submit(new NumberProducer(blockingQueue, poisonPill, poisonPillPerProducer));
        }
        for (int i = 0; i < N_CONSUMERS; i++) {
            executorService.submit(new NumberConsumer(blockingQueue, poisonPill));
        }
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("消费消息数"+ NumberConsumer.THREAD_COUNT.get());
        System.out.println("生产消息数"+ NumberProducer.THREAD_COUNT.get());
    }
}


























