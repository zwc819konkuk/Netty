package com.nettydemo.hwdemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        //1 创建启动器
        new Bootstrap()
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
                .connect(new InetSocketAddress("localhost", 7890))
                .sync()
                .channel()
                //6 向服务器发送数据
                .writeAndFlush("hello world");
    }
}
