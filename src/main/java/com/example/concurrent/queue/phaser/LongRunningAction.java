package com.example.concurrent.queue.phaser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Phaser;

public class LongRunningAction implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(LongRunningAction.class);
    private final String threadName;
    private final Phaser ph;

    public LongRunningAction(String threadName, Phaser ph) {
        this.threadName = threadName;
        this.ph = ph;
        randomWait();
        ph.register();
        logger.info("Thread{} registered during phase{}", threadName, ph.getPhase());
    }


    @Override
    public void run() {
        logger.info("Thread{} BEFORE long running action phase{}",threadName, ph.getPhase());
        ph.arriveAndAwaitAdvance();
        randomWait();
        logger.info("Thread{} AFTER long running action in phase{}", threadName, ph.getPhase());
    }
    private void randomWait(){
        try {
            Thread.sleep((long) (Math.random()*100));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
