package com.xiovr.e5bot.bot.network.impl;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.bot.packet.Packet;

public class BotConnectionClientImpl implements BotConnection {
	private int stage;

	private ChannelHandlerContext ctx;

	public BotConnectionClientImpl() {
		ctx = null;
	}
	@Override
	public void connect(@NonNull InetSocketAddress address) {
		//This method not works in client connection
	}

	@Override
	public void disconnect() {
		ctx.channel().close().addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
//				botContext.getCryptorPlugin().onDisconnected(ScriptPlugin.DISCONN_FROM_SERVER);
//				final ScriptPlugin sp = botContext.getScript();
//				if (sp != null)
//					sp.onDisconnected(ScriptPlugin.DISCONN_FROM_SERVER);
			}
		});
	}

	@Override
	public void write(Packet pck) {
		if (ctx != null) {
			ctx.write(pck);
		}
	}

	@Override
	public void init(@NonNull NioEventLoopGroup workerGroup,
			@NonNull BotContext botContext, int stage) {
//		this.botContext = botContext;
//		this.workerGroup = workerGroup;
		this.stage = stage;
	}

	@Override
	public void setHandlerContext(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public ChannelHandlerContext getHandlerContext() {
		return this.ctx;
	}

	@Override
	public boolean isConnected() {
		return ctx != null;
	}

	@Override
	public void flush() {
		if (ctx != null) {
			ctx.flush();
		}
	}

	@Override
	public void writeAndFlush(Packet pck) {
		if (ctx != null) {
			ctx.writeAndFlush(pck);
		}
	}

	@Override
	public int getStage() {
		return this.stage;
	}

}
