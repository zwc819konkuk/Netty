package com.nettydemo.test02;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread()+"execute");
                Thread.sleep(1000);
                return 100;
            }
        });
        //System.out.println(Thread.currentThread()+"wait result");
        //System.out.println(Thread.currentThread()+"result:"+future.get());
        future.addListener(new GenericFutureListener(){
            @Override
            public void operationComplete(Future future) throws Exception {
                System.out.println(Thread.currentThread()+"result:"+future.getNow());
            }
        });
    }
}
