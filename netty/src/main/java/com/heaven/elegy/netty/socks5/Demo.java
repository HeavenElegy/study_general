package com.heaven.elegy.netty.socks5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.socksx.v5.DefaultSocks5CommandRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author li.xiaoxi
 * @date 2019/06/06 15:38
 */
public class Demo {

	private Logger log = LoggerFactory.getLogger(getClass());



	public void start() throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup(2);
		EventLoopGroup worker = new NioEventLoopGroup();

		ServerBootstrap bootstrap = new ServerBootstrap()
				.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {

						ch.pipeline().addLast(new SimpleChannelInboundHandler<DefaultSocks5CommandRequest>() {
							@Override
							protected void channelRead0(ChannelHandlerContext ctx, DefaultSocks5CommandRequest msg) throws Exception {
								log.debug("{}", msg.toString());
							}
						});

					}
				});


		ChannelFuture future = bootstrap.bind(8801).sync();
		log.debug("bind port : ");
		future.channel().closeFuture().sync();

	}


}
