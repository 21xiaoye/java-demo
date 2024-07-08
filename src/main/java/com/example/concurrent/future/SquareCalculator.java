package com.example.concurrent.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SquareCalculator {
    private final ExecutorService executorService;

    public SquareCalculator(ExecutorService executorService) {
        this.executorService = executorService;
    }

    Future<Integer> calculate(Integer input) {
        return executorService.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long millis = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        SquareCalculator squareCalculator = new SquareCalculator(executor);

        Future<Integer> future1 = squareCalculator.calculate(10);
        Future<Integer> future2 = squareCalculator.calculate(100);

        while (!(future1.isDone() && future2.isDone())) {
            System.out.println(
                    String.format(
                            "future1 is %s and future2 is %s",
                            future1.isDone() ? "done" : "not done",
                            future2.isDone() ? "done" : "not done"
                    )
            );
            Thread.sleep(500);
        }
//        while (!(future1.isDone())){
//            System.out.println("还在进行计算.....");
//        }
        Integer result1 = future1.get();
//        System.out.println(result1);
        Integer result2 = future2.get();

        System.out.println(result1 + " and " + result2);
        long millis1 = System.currentTimeMillis();
        System.out.println(millis1-millis);
    }
}
