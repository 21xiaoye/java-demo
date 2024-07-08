package com.example.concurrent.future;

import java.util.concurrent.*;

public class FutureDemo {
    public String invoke() {

        String str = null;

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<String> future = executorService.submit(() -> {
            // Task
            Thread.sleep(10000);
            return "Hello world";
        });

        future.cancel(false);

        try {
            future.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e1) {
            e1.printStackTrace();
        }

        if (!future.isDone() && !future.isCancelled()) {
            try {
                str = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return str;
    }

    public static void main(String[] args) {
        FutureDemo futureDemo = new FutureDemo();
        String invoke = futureDemo.invoke();
        System.out.println(invoke);
    }

}
