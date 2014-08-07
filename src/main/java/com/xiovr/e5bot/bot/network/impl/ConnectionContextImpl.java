package com.xiovr.e5bot.bot.network.impl;

import org.eclipse.jdt.annotation.NonNull;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.xiovr.e5bot.bot.BotEnvironment;
import com.xiovr.e5bot.bot.network.ConnectionContext;

public class ConnectionContextImpl implements ConnectionContext {

	private NioEventLoopGroup clientEventGroup;
	private NioEventLoopGroup bossClientEventGroup;
	private NioEventLoopGroup serverEventGroup;
	private BotEnvironment botEnvironment;
	private ServerBootstrap bs;
	private ChannelFuture bossFuture;
	public void init(@NonNull BotEnvironment botEnvironment)
	{
		this.botEnvironment = botEnvironment;

		// Create client and server nio events;
		clientEventGroup = new NioEventLoopGroup();
		bossClientEventGroup = new NioEventLoopGroup();
		serverEventGroup = new NioEventLoopGroup(2);
		
		// Start boss client listener
		bs = new ServerBootstrap();
		bs.group(bossClientEventGroup, clientEventGroup);
		bs.channel(NioServerSocketChannel.class);
		bs.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast();
				
			} });
		bs.option(ChannelOption.SO_BACKLOG, 128);
		bs.childOption(ChannelOption.SO_KEEPALIVE, true);
//		bossFuture = bs.bind(botEnvironment.getClientAddress());

		
	}
	public void dispose()
	{
		try {
			bossFuture.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		
			clientEventGroup.shutdownGracefully();
			serverEventGroup.shutdownGracefully();
			bossClientEventGroup.shutdownGracefully();
		}
	}
}
