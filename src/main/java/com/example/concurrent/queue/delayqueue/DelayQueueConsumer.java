package com.example.concurrent.queue.delayqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayQueueConsumer implements Runnable{
    private BlockingQueue<DelayObject> delayObjects;
    private final Integer numberOfElementsToTake;
    public final AtomicInteger numberOfConsumeElements = new AtomicInteger();

    public DelayQueueConsumer(BlockingQueue<DelayObject> delayObjects,
                              Integer numberOfElementsToTake) {
        this.delayObjects = delayObjects;
        this.numberOfElementsToTake = numberOfElementsToTake;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToTake; i++) {
            try {
                DelayObject take = delayObjects.take();
                numberOfConsumeElements.incrementAndGet();
                System.out.println("Consumer take:"+take);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
