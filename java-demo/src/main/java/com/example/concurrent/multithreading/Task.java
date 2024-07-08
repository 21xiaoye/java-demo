package com.example.concurrent.multithreading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环屏障
 */
public class Task implements Runnable {
    private static CyclicBarrier cyclicBarrier;

    public Task() {
        cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("All");
        });
    }

    @Override
    public void run() {
        try {
            System.out.println("hello");
            cyclicBarrier.await();
            System.out.println("world");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Task task = new Task();
        task.start();
    }

    public void start() {
        Thread t1 = new Thread(new Task(), "T1");
        Thread t2 = new Thread(new Task(), "T2");
        Thread t3 = new Thread(new Task(), "T3");

        t1.start();
        t2.start();
        t3.start();
    }
}