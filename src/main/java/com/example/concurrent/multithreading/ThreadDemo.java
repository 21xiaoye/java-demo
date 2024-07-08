package com.example.concurrent.multithreading;

public class ThreadDemo extends Thread{
    private Thread t;
    private String threadName;

    public ThreadDemo(String threadName){
        this.threadName = threadName;
    }
    @Override
    public void run() {
        System.out.println("Creating"+ threadName);
        try {
            for (int i = 0; i < 4; i++) {
                System.out.println("thread:"+threadName+","+i);
                Thread.sleep(500);
            }
        }catch (InterruptedException e){
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start(){
        System.out.println("Starting"+threadName);
        if(t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo("thread -1");
        threadDemo.start();

        ThreadDemo threadDemo1 = new ThreadDemo("thread -2");
        threadDemo1.start();
    }
}
