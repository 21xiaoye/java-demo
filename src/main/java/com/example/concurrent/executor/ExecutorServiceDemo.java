package com.example.concurrent.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ExecutorServiceDemo {
    Runnable runnableTask =()->{
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("hello");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    };

    Callable<String> callableTask = ()->{
        TimeUnit.SECONDS.sleep(1);
        return "Task's execution";
    };
    private List<Callable<String>> callableList = new ArrayList<>();

    public List<Callable<String>> getCallableList() {
        return callableList;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ExecutorServiceDemo executorServiceDemo = new ExecutorServiceDemo();
        executorService.execute(executorServiceDemo.runnableTask);

        Future<String> future = executorService.submit(executorServiceDemo.callableTask);
        String s = future.get();
        System.out.println(s);

        IntStream.range(0,10).forEach(count->{
            executorServiceDemo.getCallableList().add(executorServiceDemo.callableTask);
        });
        List<Future<String>> futures = executorService.invokeAll(executorServiceDemo.callableList);
        futures.stream().forEach(item->{
            try {
                String s1 = item.get();
                System.out.println("->"+s1);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(5, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }
        }catch (InterruptedException e){
            executorService.shutdownNow();
        }
    }
}
