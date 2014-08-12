package com.xiovr.e5bot.bot.network;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.network.impl.ClientConnectionHandlerImpl;
import com.xiovr.e5bot.bot.network.impl.ConnectionDecoderImpl;
import com.xiovr.e5bot.bot.network.impl.ConnectionEncoderImpl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ClientConnectionChannelInitializer extends
		ChannelInitializer<SocketChannel> {

	private int stage;
	private List<BotContext> proxyBots;

	public ClientConnectionChannelInitializer(
			@NonNull List<BotContext> proxyBots, int stage) {
		this.stage = stage;
		this.proxyBots = proxyBots;

	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ch.pipeline().addLast(new ConnectionDecoderImpl(),
				new ConnectionEncoderImpl(),
				new ClientConnectionHandlerImpl(proxyBots, stage));
	}

}
