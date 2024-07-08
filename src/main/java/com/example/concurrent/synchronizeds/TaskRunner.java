package com.example.concurrent.synchronizeds;

import java.util.concurrent.TimeUnit;

public class TaskRunner {
    private static int number;
    private volatile static boolean ready;
    private volatile int count =0;
    void incrementCount(){
        count++;
    }
    public int  getCount(){
        return count;
    }
    private static class Reader extends Thread{
        @Override
        public void run() {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            while (!ready){
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException{
//        new Reader().start();
//        number = 42;
//        ready = true;
        TaskRunner taskRunner = new TaskRunner();
        new Thread(taskRunner::incrementCount).start();
        Thread.sleep(1000);
        Thread thread = new Thread(taskRunner::incrementCount);
        thread.start();
        Thread.sleep(1000);
        new Thread(()->{
            int count1 = taskRunner.getCount();
            System.out.println(count1);
        }).start();

        new Thread(()->{
            int count1 = taskRunner.getCount();
            System.out.println(count1);
        }).start();
    }
}
