package com.nettydemo.test02;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;

import java.util.concurrent.ExecutionException;

public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventLoop = new NioEventLoopGroup().next();

        //主动创建promise对象,结果的容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        //任意一个线程计算完毕后向promise填充结果
        new Thread(() -> {
            System.out.println("calculate start");
            try {
                int i = 1/0;
                Thread.sleep(1000);
                promise.setSuccess(80);
            } catch (Exception e) {
                promise.setFailure(e);
            }

        }).start();

        //一个线程想获得结果
        System.out.println("wait result...");
        System.out.println("result:" + promise.get());
    }
}
