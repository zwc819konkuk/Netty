package com.nettydemo.hwdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        //1 启动器，负责组装netty组件，启动服务器
        new ServerBootstrap()
                //2 group组
                .group(new NioEventLoopGroup())
                //3 选择服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                //4 boss负责处理连接，worker（child）负责处理读写，childHandler决定了worker（child）做哪些工作
                .childHandler(new ChannelInitializer<NioSocketChannel>() {//5 channel代表和客户端进行数据读写的通道 initializer初始化，负责添加别的handler
                    //连接建立之后调用初始化方法
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //7 添加具体的handler
                        ch.pipeline().addLast(new StringDecoder());//将传过来的byteBuf 转换为字符串
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() { //自定义handler
                            @Override//处理读事件
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                //6 绑定监听端口
                .bind(8889);//绑定监听端口
    }
}
