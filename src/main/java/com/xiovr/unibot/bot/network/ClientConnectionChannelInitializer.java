package com.xiovr.unibot.bot.network;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.network.impl.ClientConnectionHandlerImpl;
import com.xiovr.unibot.bot.network.impl.ConnectionDecoderImpl;
import com.xiovr.unibot.bot.network.impl.ConnectionEncoderImpl;

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

	@SuppressWarnings("null")
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ch.pipeline().addLast(new ConnectionDecoderImpl(),
				new ConnectionEncoderImpl(),
				new ClientConnectionHandlerImpl(proxyBots, stage));
	}

}
