package com.example.concurrent.multithreading;

import java.util.concurrent.BrokenBarrierException;

public class BaeldungThreadFactoryTest implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("hello");
        }finally {
            BaeldungThreadFactory.THREAD_COUNT.decrementAndGet();
            System.out.println(BaeldungThreadFactory.THREAD_COUNT.get());
        }
    }
}
