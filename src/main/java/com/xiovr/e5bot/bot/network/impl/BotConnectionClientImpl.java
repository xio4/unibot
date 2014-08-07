package com.xiovr.e5bot.bot.network.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.bot.packet.Packet;

public class BotConnectionClientImpl implements BotConnection {

	private BotContext botContext;
	private NioEventLoopGroup workerGroup;
	private int stage;

	@Override
	public void connect(@NonNull InetSocketAddress address) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(Packet pck) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(@NonNull NioEventLoopGroup workerGroup,
			@NonNull BotContext botContext, int stage) {
		this.botContext = botContext;
		this.workerGroup = workerGroup;
		this.stage = stage;
	}

	@Override
	public void setHandlerContext(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChannelHandlerContext getHandlerContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeAndFlush(Packet pck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStage() {
		return this.stage;
	}

}
