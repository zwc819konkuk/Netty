package com.nettydemo.test01;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

public class TestEventLoop {
    public static void main(String[] args) {
         /*
            DEFAULT_EVENT_LOOP_THREADS 使用默认构造方法的话，事件循环对象是cpu核数乘2
            private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(
                                1,
                                SystemPropertyUtil.getInt("io.netty.eventLoopThreads",
                                NettyRuntime.availableProcessors() * 2)
            );

         */
        //创建循环组
        EventLoopGroup group = new NioEventLoopGroup(2);//io事件，普通任务，定时任务
        //获取下一个事件循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        //执行普通任务
        group.next().execute(()->{
            System.out.println("kk");
        });
        //定时任务
        group.next().scheduleAtFixedRate(() -> {
            System.out.println("ok");
        }, 1, 1, TimeUnit.SECONDS);

    }
}
