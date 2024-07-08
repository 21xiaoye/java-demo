package com.example.concurrent.multithreading;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest implements Runnable{
    private static CountDownLatch countDownLatch;
    public CountDownLatchTest(){
        countDownLatch = new CountDownLatch(3);
    }
    @Override
    public void run() {
        try{
            System.out.println("hello");
            countDownLatch.countDown();
            countDownLatch.await();
            System.out.println("world");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void start(){

        Thread thread1 = new Thread(new CountDownLatchTest(), "thread1");
        Thread thread2 = new Thread(new CountDownLatchTest(), "thread2");
        Thread thread3 = new Thread(new CountDownLatchTest(), "thread3");

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void main(String[] args) {
        CountDownLatchTest a = new CountDownLatchTest();
        a.start();
    }
}
