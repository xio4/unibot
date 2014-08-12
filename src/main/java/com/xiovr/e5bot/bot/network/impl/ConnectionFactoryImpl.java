package com.xiovr.e5bot.bot.network.impl;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotEnvironment;
import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.bot.network.ClientConnectionChannelInitializer;
import com.xiovr.e5bot.bot.network.ConnectionFactory;

public class ConnectionFactoryImpl implements ConnectionFactory {

	private NioEventLoopGroup clientEventGroup;
	private NioEventLoopGroup bossClientEventGroup;
	private NioEventLoopGroup serverEventGroup;
	private BotEnvironment botEnvironment;
	private ServerBootstrap bs;
	private List<ChannelFuture> bossFutures;
	private List<BotContext> proxyBots;
	
	@Override
	public void init(@NonNull BotEnvironment botEnvironment)
	{
		// Create client and server nio events;
		clientEventGroup = new NioEventLoopGroup();
		bossClientEventGroup = new NioEventLoopGroup();
		serverEventGroup = new NioEventLoopGroup(2);
		this.botEnvironment = botEnvironment;
	}

	@Override
	public void createProxyConnections(
			@NonNull List<BotContext> proxyBots) {
		this.proxyBots = proxyBots;



		// Start boss client listeners
		for (int stage = 0; stage < botEnvironment.getServerAddresses().size(); ++stage) {
			bs = new ServerBootstrap();
			bs.group(bossClientEventGroup, clientEventGroup);
			bs.channel(NioServerSocketChannel.class);
			bs.childHandler(new ClientConnectionChannelInitializer(proxyBots,
					stage));
			bs.option(ChannelOption.SO_BACKLOG, 128);
			bs.childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture cf = bs.bind(botEnvironment.getClientAddresses().get(stage));

			bossFutures.add(cf);
		}
	}
	@SuppressWarnings("null")
	@Override
	public void createBotConnectionServer(@NonNull BotContext botContext) {
		BotConnection botConn = new BotConnectionServerImpl();
		botContext.addServerConnectionStage(botConn);
		botConn.init(serverEventGroup, botContext, botContext.getServerConnections().size() - 1);
	}

	@Override
	public void createBotConnectionClient(@NonNull BotContext botContext) {
		BotConnection botConn = new BotConnectionClientImpl();
		botContext.addClientConnectionStage(botConn);
		botConn.init(clientEventGroup, botContext, botContext.getClientConnections().size() - 1);
	}
	@Override
	public void dispose() {
		try {
			for (ChannelFuture ch: bossFutures)
				ch.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			clientEventGroup.shutdownGracefully();
			serverEventGroup.shutdownGracefully();
			bossClientEventGroup.shutdownGracefully();
		}
	}
}
