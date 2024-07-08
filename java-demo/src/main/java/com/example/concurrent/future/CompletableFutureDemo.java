package com.example.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {
    public static String getStr() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return "Hello world";
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return CompletableFutureDemo.getStr();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        while (!future.isDone()){
            System.out.println("hhhh");
        }
        CompletableFuture<String> apply = future.thenApply(s -> s + "......");

        CompletableFuture<Void> completableFuture = apply.thenAccept(s -> {
            System.out.println(s);
        });

        CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(s -> s+" world")
                .thenAccept(s1->{
                    System.out.println(s1);
                });
        CompletableFuture.supplyAsync(()->"hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "world"))
                .thenAccept(s1->{
                    System.out.println(s1);
                });
        CompletableFuture.supplyAsync(()->"hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(()->"world"),(s1,s2)->{
                    System.out.println(s1+s2);
                });
    }
}
