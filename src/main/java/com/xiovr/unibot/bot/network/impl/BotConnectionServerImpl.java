package com.xiovr.unibot.bot.network.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.network.BotConnection;
import com.xiovr.unibot.bot.packet.Packet;

/**
 * @author xio4
 * Major controller for server connection
 */
public class BotConnectionServerImpl implements BotConnection {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BotConnectionServerImpl.class);
	private BotContext botContext;
	private NioEventLoopGroup workerGroup;
	private Bootstrap bs;
	private ChannelFuture cf;
	private ChannelHandlerContext ctx;
	private int stage;
	private boolean bDisconnectPermit;

	public BotConnectionServerImpl() {
		this.ctx = null;
		this.bDisconnectPermit = true;
	}

	@Override
	public void connect(@NonNull InetSocketAddress address) {
		bs = new Bootstrap();
		bs.group(workerGroup);
		bs.channel(NioSocketChannel.class);
		bs.option(ChannelOption.SO_KEEPALIVE, true);
		bs.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ConnectionDecoderImpl(),
						new ConnectionEncoderImpl(),
						new ServerConnectionHandlerImpl(botContext, stage));
			}
		});
		cf = bs.connect(address);
		cf.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
//				botContext.getCryptorPlugin().onConnected(ScriptPlugin.CONN_TO_SERVER);
//				final ScriptPlugin sp = botContext.getScript();
//				if (sp != null)
//					sp.onConnected(ScriptPlugin.CONN_TO_SERVER);
//				bConnected = true;

				}
			}
		}); 
	}

	@Override
	public void disconnect() {
		cf.channel().close().addListener(new ChannelFutureListener() {
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
		this.botContext = botContext;
		this.workerGroup = workerGroup;
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
		else {
			System.out.println("Server ctx is null!");
		}
	}

	@Override
	public int getStage() {
		return this.stage;
	}

	@Override
	public boolean getDisconnectionPermit() {
		return this.bDisconnectPermit;
	}

	@Override
	public void setDisconnectionPermit(boolean bPermit) {
		this.bDisconnectPermit = bPermit;
	}

}
