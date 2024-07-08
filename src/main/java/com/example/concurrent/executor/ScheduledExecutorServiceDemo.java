package com.example.concurrent.executor;

import java.util.concurrent.*;

public class ScheduledExecutorServiceDemo {
    Runnable runnableTest = ()->{
        System.out.println("hello");
    };

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorServiceDemo demo = new ScheduledExecutorServiceDemo();

//        executor.schedule(demo.runnableTest, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(demo.runnableTest, 1,1, TimeUnit.SECONDS);
    }
}
