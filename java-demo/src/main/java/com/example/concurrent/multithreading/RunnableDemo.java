package com.example.concurrent.multithreading;

public class RunnableDemo implements Runnable{
    private Thread t;
    private String threadName;

    RunnableDemo(String threadName){
        this.threadName = threadName;
        System.out.println("Creating"+threadName);
    }

    @Override
    public void run() {
        System.out.println("Running"+threadName);
        try {
            for (int i = 0; i < 4; i++) {
                System.out.println("thread:"+threadName+","+i);
                Thread.sleep(50);
            }
        }catch (InterruptedException e){
            System.out.println("thread"+threadName+"interrupted");
        }
        System.out.println("Thread"+threadName+"exiting");
    }

    public void start(){
        System.out.println("Starting"+threadName);
        if(t == null){
            t = new Thread(this, threadName);
            t.start();;
        }
    }

    public static void main(String[] args) {
        RunnableDemo runnableDemo = new RunnableDemo("thread -1");
        runnableDemo.start();

        RunnableDemo runnableDemo1 = new RunnableDemo("thread -2");
        runnableDemo1.start();
    }
}


















