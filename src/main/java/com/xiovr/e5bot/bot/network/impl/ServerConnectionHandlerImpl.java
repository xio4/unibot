package com.xiovr.e5bot.bot.network.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.network.ServerConnectionHandler;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.PacketPool;
import com.xiovr.e5bot.bot.packet.RingBufferPool;
import com.xiovr.e5bot.plugin.CryptorPlugin;
import com.xiovr.e5bot.plugin.ScriptPlugin;

public class ServerConnectionHandlerImpl extends
	ChannelInboundHandlerAdapter implements ServerConnectionHandler
//		SimpleChannelInboundHandler<Packet> implements ServerConnectionHandler {
{
	private static final Logger logger = LoggerFactory.getLogger(ServerConnectionHandlerImpl.class);
	private BotContext botContext;
	private RingBufferPool<Packet> readBufPool;

	public ServerConnectionHandlerImpl(BotContext botContext) {
//		super();
		this.botContext = botContext;
		this.readBufPool = botContext.getReadBuffer();
	}

	@SuppressWarnings("null")
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		PacketPool.free(readBufPool.put((Packet)msg));
		// See source code this class
		//ReferenceCountUtil.release(msg); 
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		botContext.getServerConnection().setHandlerContext(ctx);
		final CryptorPlugin cp = botContext.getCryptorPlugin();
		if (cp != null)
			cp.onConnected(ScriptPlugin.CONN_TO_SERVER);
		final ScriptPlugin script = botContext.getScript();
		long startTime = System.currentTimeMillis();
		if (script != null ) {
			script.onConnected(ScriptPlugin.CONN_TO_SERVER);
			long endTime = System.currentTimeMillis();
			if (endTime - startTime > ScriptPlugin.MAX_WORK_TIME)
				logger.error("Script method onConnected with name "+script.getName() + " works " + (endTime - startTime) + "ms");
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		botContext.getServerConnection().setHandlerContext(null);
		final CryptorPlugin cp = botContext.getCryptorPlugin();
		if (cp != null)
			cp.onDisconnected(ScriptPlugin.DISCONN_FROM_SERVER);
		final ScriptPlugin script = botContext.getScript();
		long startTime = System.currentTimeMillis();
		if (script != null ) {
			script.onDisconnected(ScriptPlugin.DISCONN_FROM_SERVER);
			long endTime = System.currentTimeMillis();
			if (endTime - startTime > ScriptPlugin.MAX_WORK_TIME)
				logger.error("Script method onDisconnected with name "+script.getName() + " works " + (endTime - startTime) + "ms");
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
//		super.exceptionCaught(ctx, cause);
	}
}
