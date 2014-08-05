package com.xiovr.e5bot.bot.network.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.xiovr.e5bot.bot.BotEnvironment;
import com.xiovr.e5bot.bot.network.ClientConnectionRunnable;

public class ClientConnectionRunnableImpl implements ClientConnectionRunnable {

	private BotEnvironment botEnvironment;
	@Override
	public void setBotEnvironment(BotEnvironment botEnvironment)
	{
		this.botEnvironment = botEnvironment;
	}
	@Override
	public BotEnvironment getBotEnvironment()
	{
		return this.botEnvironment;
	}
	@Override
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup(2);
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.
			group(bossGroup, workerGroup).
			channel(NioServerSocketChannel.class).
			childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast();
			
				}
			}).
			option(ChannelOption.SO_BACKLOG, 128).
//			option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT).
//			childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT).
			childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture cf = sb.bind(botEnvironment.getClientAddress()).sync();
			
			cf.channel().closeFuture().sync();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

}
