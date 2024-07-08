package com.example.concurrent.queue.delayqueue;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class DelayQueueProducer implements Runnable{
    private BlockingQueue<DelayObject> delayObjects;
    private final Integer numberOfElementsToProduce;
    private final Integer delayOfEachProduceMessageMilliseconds;

    public DelayQueueProducer(BlockingQueue<DelayObject> delayObjects,
                              Integer numberOfElementsToProduce,
                              Integer delayOfEachProduceMessageMilliseconds) {
        this.delayObjects = delayObjects;
        this.numberOfElementsToProduce = numberOfElementsToProduce;
        this.delayOfEachProduceMessageMilliseconds = delayOfEachProduceMessageMilliseconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            DelayObject object = new DelayObject(UUID.randomUUID().toString(), delayOfEachProduceMessageMilliseconds);
            System.out.println("Put Object="+object);
            try {
                delayObjects.put(object);
                Thread.sleep(1500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}








































