package com.example.concurrent.thread;

public class WaitingState implements Runnable{
    public static Thread t1;
    @Override
    public void run() {
        Thread t2 = new Thread(new DemoWaitingStateRunnable());
        t2.start();
        try {
            t2.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        t1 = new Thread(new WaitingState());
//        t1.start();
//        Thread thread = new Thread(new DemoWaitingStateRunnable());
//        thread.start();
//        Thread.sleep(100);
//        System.out.println(thread.getState());

        Thread thread = new Thread(new DemoWaitingStateRunnable());
        thread.start();
        Thread.sleep(2000);
        System.out.println(thread.getState());
    }
}
