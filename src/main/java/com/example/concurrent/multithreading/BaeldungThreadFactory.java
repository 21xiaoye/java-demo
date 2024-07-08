package com.example.concurrent.multithreading;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BaeldungThreadFactory implements ThreadFactory {
    public static AtomicInteger THREAD_COUNT = new AtomicInteger(0);

    private String threadName;
    public BaeldungThreadFactory(){
    }
    public BaeldungThreadFactory(String threadName){
        this.threadName = threadName;
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, threadName + "-Thread_" + THREAD_COUNT.incrementAndGet());
        System.out.println("创建线程"+thread.getName());
        return thread;
    }

//    public static void main(String[] args) throws InterruptedException {
//        // 创建一个自定义的线程工厂
//        BaeldungThreadFactory factory = new BaeldungThreadFactory("Baeldung");
//
//        // 创建一个固定大小的线程池，使用自定义的线程工厂
//        ExecutorService executorService = Executors.newFixedThreadPool(10, factory);
//
//        // 提交一些任务给线程池
//        for (int i = 0; i < 10; i++) {
//            executorService.submit(new BaeldungThreadFactoryTest());
//        }
//
//        // 关闭线程池，等待所有任务完成
//        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.MINUTES);
//
//        // 输出活跃线程数，应为0
//        System.out.println("活跃线程数: " + BaeldungThreadFactory.THREAD_COUNT.get());
//    }
}
