package com.nettydemo.test02;

import java.util.concurrent.*;


public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //1 线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        //2 提交任务
        Future<Integer> future = threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread()+"execute");
                Thread.sleep(1000);
                return 50;
            }
        });
        //3 主线程通过future获取结果 同步等待结果
        System.out.println(Thread.currentThread()+"wait result");
        System.out.println(Thread.currentThread()+"result:"+future.get());

    }
}
