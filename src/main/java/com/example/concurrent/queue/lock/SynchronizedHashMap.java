package com.example.concurrent.queue.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SynchronizedHashMap {
    private final static int THREAD_COUNT =4;
    private Logger logger = LoggerFactory.getLogger(SynchronizedHashMap.class);
    private static Map<String, String> syncHashMap = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    public void put(String key, String value) throws InterruptedException{
        try {
            writeLock.lock();
            logger.info(Thread.currentThread().getName() + " writing");
            syncHashMap.put(key, value);
            Thread.sleep(1000);
        }finally {
            writeLock.unlock();
        }
    }

    public String get(String key){
        try {
            readLock.lock();
            logger.info(Thread.currentThread().getName()+" reading");
            return syncHashMap.get(key);
        }finally {
            readLock.unlock();
        }
    }

    public String remove(String key){
        try {
            writeLock.lock();
            return syncHashMap.remove(key);
        }finally {
            writeLock.unlock();
        }
    }

    public boolean containsKey(String key){
        try {
            readLock.lock();
            return syncHashMap.containsKey(key);
        }finally {
            readLock.unlock();
        }
    }

    boolean isReadLockAvailable(){
        return readLock.tryLock();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        SynchronizedHashMap map = new SynchronizedHashMap();
        executorService.execute(new Thread(new Writer(map),"Writer1"));
        executorService.execute(new Thread(new Writer(map),"Writer2"));
        executorService.execute(new Thread(new Reader(map), "Reader1"));
        executorService.execute(new Thread(new Reader(map), "Reader2"));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(syncHashMap.size());
    }
    public static class Reader implements Runnable{
        SynchronizedHashMap object;
        Reader(SynchronizedHashMap object){
            this.object = object;
        }
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(object.get("key"+i));
            }
        }
    }

    private static class Writer implements Runnable{
        SynchronizedHashMap object;
        Writer(SynchronizedHashMap object){
            this.object = object;
        }
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    object.put("key" + i, "value" + i);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}























