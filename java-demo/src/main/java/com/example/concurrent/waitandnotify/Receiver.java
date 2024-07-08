package com.example.concurrent.waitandnotify;

import java.util.concurrent.ThreadLocalRandom;

public class Receiver implements Runnable{
    private Data data;

    public Receiver(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
//        for (String receiveString = data.receive();!"End".equals(receiveString); receiveString = data.receive()){
//            System.out.println(receiveString);
//            try {
//                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
//            }catch (InterruptedException e){
//                Thread.currentThread().interrupt();
//                System.out.println("Thread Interrupted");
//            }
//        }
        while (true){
            System.out.println(data.receive());
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted");
            }
        }
    }
}
