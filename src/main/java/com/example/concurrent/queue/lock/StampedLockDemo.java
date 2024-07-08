package com.example.concurrent.queue.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;
public class StampedLockDemo {
    private Map<String,String> map = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final StampedLock lock = new StampedLock();
    public void put(String key, String value){
        long writeLock = lock.writeLock();
        try {
            logger.info(Thread.currentThread().getName()+"acquired the write lock with stamp"+writeLock);
            map.put(key,value);
        }finally {
            lock.unlock(writeLock);
            logger.info(Thread.currentThread().getName()+" unlock the write lock with stamp"+writeLock);
        }
    }

    public String get(String key) throws InterruptedException{
        long readLock = lock.readLock();
        logger.info(Thread.currentThread().getName()+" acquired the read lock with stamp"+readLock);
        try {
            Thread.sleep(5000);
            return map.get(key);
        } finally {
            lock.unlock(readLock);
            logger.info(Thread.currentThread().getName() + " unlocked the read lock with stamp"+readLock);
        }
    }

    private String readWithOptimisticLock(String key) throws InterruptedException {
        long read = lock.tryOptimisticRead();
        String value = map.get(key);
        if(!lock.validate(read)){
            read = lock.readLock();
            try {
                Thread.sleep(5000);
                return map.get(key);
            }finally {
                lock.unlock(read);
                logger.info(Thread.currentThread().getName()+" unlocked the read lock with stamp"+read);
            }
        }
        return value;
    }

    public static void main(String[] args) {
        final int threadCount = 4;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        StampedLockDemo stampedLockDemo = new StampedLockDemo();
        Runnable writeTask = ()->{
            stampedLockDemo.put("key1", "value1");
        };

        Runnable readTask = ()->{
            try {
                stampedLockDemo.get("key1");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };
        Runnable readOptimisticTask =()->{
            try {
                stampedLockDemo.readWithOptimisticLock("key1");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };
        executorService.submit(writeTask);
        executorService.submit(writeTask);
        executorService.submit(readTask);
        executorService.submit(readOptimisticTask);

        executorService.shutdown();
    }
}
