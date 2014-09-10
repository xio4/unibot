/**
 * Copyright (c) 2014 xio4
 * Universal bot for lineage-like games (Archeage, Lineage2 etc)
 *
 * This file is part of Unibot.
 *
 * Unibot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.xiovr.unibot.bot.network.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.network.ServerConnectionHandler;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.ScriptPlugin;

public class ServerConnectionHandlerImpl extends
	ChannelInboundHandlerAdapter implements ServerConnectionHandler
//		SimpleChannelInboundHandler<Packet> implements ServerConnectionHandler {
{
	private static final Logger logger = LoggerFactory.getLogger(ServerConnectionHandlerImpl.class);
	private BotContext botContext;
	private RingBufferPool<Packet> readBufPool;
	private int stage;

	public ServerConnectionHandlerImpl(BotContext botContext, int stage) {
//		super();
		this.botContext = botContext;
		this.readBufPool = botContext.getReadBuffer();
		this.stage = stage;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		final Packet pck = (Packet)msg;
		pck.setConnStage(stage);
		pck.setTime(System.currentTimeMillis());
		pck.setType(Packet.RAW_PCK_FROM_SERVER);
//		PacketPool.free(readBufPool.put(pck));
//		System.out.println("Packet read");
		readBufPool.put(pck);
		// See source code this class
		//ReferenceCountUtil.release(msg); 
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		botContext.getServerConnections().get(stage).setHandlerContext(ctx);
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
		botContext.getServerConnections().get(stage).setHandlerContext(null);
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
