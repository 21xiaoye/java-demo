package com.example.concurrent.synchronizeds;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedMethods {
    private int sum = 0;
    private static int staticSum = 0;
    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }

    public static int getStaticSum(){
        return staticSum;
    }

    /**
     *  同步实例方法
     */
    public synchronized  void calculate(){
        setSum(getSum()+1);
    }
    /**
     * 同步实例静态方法
     */
    public static synchronized void syncStaticCalculate(){
        staticSum ++;
    }
    /**
     * 同步代码块
     */
    public void performSynchronisedTask(){
        synchronized (this){
            setSum(getSum()+1);
        }
    }

    public static void main(String[] args) {
        /**
         * 测试锁的可重入性
         */
        ReentrantLock lock = new ReentrantLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 1;
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "第" + index + "获得锁" + lock);
                    while (index < 10000) {
                        try {
                            lock.lock();
                            System.out.println(Thread.currentThread().getName() + "第" + ++index + "获得锁" + lock);
                        }  finally {
                            lock.unlock();
                        }
                    }
                }finally {
                    lock.unlock();
                }
            }
        },"thread1").start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    lock.lock();

                    for (int i = 0; i < 10; i++) {
                        System.out.println("threadName:" + Thread.currentThread().getName());
                        try {
                            Thread.sleep(new Random().nextInt(200));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        },"thread2").start();

    }
}
