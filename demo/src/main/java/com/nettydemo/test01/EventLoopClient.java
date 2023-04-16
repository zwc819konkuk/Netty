package com.nettydemo.test01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

import static sun.java2d.opengl.OGLRenderQueue.sync;

public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        //1 创建启动器
        ChannelFuture cf = new Bootstrap()
                //2 添加eventLoop
                .group(new NioEventLoopGroup())
                //3 选择客户端channel实现
                .channel(NioSocketChannel.class)
                //4 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override//在连接建立后执行
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                //5 连接到服务器
                // 异步非阻塞，main线程发起调用，真正执行connect是另一个NioEventLoopGroup中的一个nio线程
                .connect(new InetSocketAddress("localhost", 9990));
        //方式一
        //使用sync同步处理，阻塞住当前线程，等nio线程建立好，主线程同步等待结束，会放行
        ChannelFuture sync = cf.sync();
        //如果没有sync阻塞的话，这时拿到的channel 是一个没有建立连接的channel
        Channel channel = cf.channel();
        channel.writeAndFlush("hello");

        //方式二
        //使用addListener（回调对象）方法异步处理结果
        cf.addListener(new ChannelFutureListener() {
            @Override
            // 在nio线程连接建立好之后，会调用operationComplete方法
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel1 = channelFuture.channel();
                System.out.println(channel1);
                channel1.writeAndFlush("hello");
            }
        });

    }
}
