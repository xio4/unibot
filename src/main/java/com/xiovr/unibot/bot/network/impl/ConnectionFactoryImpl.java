package com.xiovr.unibot.bot.network.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.network.BotConnection;
import com.xiovr.unibot.bot.network.ClientConnectionChannelInitializer;
import com.xiovr.unibot.bot.network.ConnectionFactory;

public class ConnectionFactoryImpl implements ConnectionFactory {

	class EventsGroup {
		public EventsGroup() {
			this.clientEventGroup = new NioEventLoopGroup();
			this.bossClientEventGroup = new NioEventLoopGroup();
			this.serverEventGroup = new NioEventLoopGroup(2);
		}

		public void shutdownGracefully() {
			this.clientEventGroup.shutdownGracefully();
			this.serverEventGroup.shutdownGracefully();
			this.bossClientEventGroup.shutdownGracefully();
		}

		public NioEventLoopGroup clientEventGroup;
		public NioEventLoopGroup bossClientEventGroup;
		public NioEventLoopGroup serverEventGroup;
	}

	private List<EventsGroup> eventsList;
	private BotEnvironment botEnvironment;
	private ServerBootstrap bs;
	private List<ChannelFuture> bossFutures;

	@Override
	public void init(@NonNull BotEnvironment botEnvironment) {
		// Create client and server nio events;
		this.botEnvironment = botEnvironment;
		this.bossFutures = new ArrayList<ChannelFuture>();
		this.eventsList = new ArrayList<EventsGroup>();
	}

	@Override
	public void createProxyListeners(@NonNull List<BotContext> proxyBots) {

		// Start boss client listeners
		for (int stage = 0; stage < botEnvironment.getClientAddresses().size(); ++stage) {
			EventsGroup eg = new EventsGroup();

			bs = new ServerBootstrap();
			bs.group(eg.bossClientEventGroup, eg.clientEventGroup);
			bs.channel(NioServerSocketChannel.class);
			bs.childHandler(new ClientConnectionChannelInitializer(proxyBots,
					stage));
			bs.option(ChannelOption.SO_BACKLOG, 128);
			bs.childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture cf = bs.bind(botEnvironment.getClientAddresses().get(
					stage));

			eventsList.add(eg);
			bossFutures.add(cf);
		}
	}

	@SuppressWarnings("null")
	@Override
	public void createBotConnectionServer(@NonNull BotContext botContext,
			int stage) {
		BotConnection botConn = new BotConnectionServerImpl();
		EventsGroup eg = eventsList.get(stage);
		botContext.addServerConnection(botConn);
		botConn.init(eg.serverEventGroup, botContext, stage);
	}

	@SuppressWarnings("null")
	@Override
	public void createBotConnectionClient(@NonNull BotContext botContext,
			int stage) {
		BotConnection botConn = new BotConnectionClientImpl();
		EventsGroup eg = eventsList.get(stage);
		botContext.addClientConnection(botConn);
		botConn.init(eg.clientEventGroup, botContext, stage);
	}

	@Override
	public void dispose() {
		try {
			for (ChannelFuture ch : bossFutures) {
				ch.channel().close();
				ch.channel().closeFuture().sync();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			for (EventsGroup eg: eventsList) {
				eg.shutdownGracefully();
			}

		}
	}

	@Override
	public void finalize() {
		dispose();
	}
}
