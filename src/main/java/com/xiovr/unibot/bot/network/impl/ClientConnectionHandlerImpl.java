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


import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.network.ClientConnectionHandler;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.ScriptPlugin;

public class ClientConnectionHandlerImpl extends
	ChannelInboundHandlerAdapter implements ClientConnectionHandler
//		SimpleChannelInboundHandler<Packet> implements ServerConnectionHandler {
{
	private static final Logger logger = LoggerFactory.getLogger(ClientConnectionHandlerImpl.class);
	private List<BotContext> proxyBots;
	private BotContext botContext;
	private RingBufferPool<Packet> readBufPool;
	private int stage;

//	public ClientConnectionHandlerImpl(@NonNull List<BotContext> proxyBots, int stage) {
	public ClientConnectionHandlerImpl(@NonNull List<BotContext> proxyBots, int stage) {
		this.proxyBots = proxyBots;
		this.stage = stage;
		this.botContext = null;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
//		System.out.println("Client Channel read stage =" + stage);
		if (readBufPool != null) {
			final Packet pck = (Packet)msg;
			pck.setConnStage(stage);
			pck.setTime(System.currentTimeMillis());
			pck.setType(Packet.RAW_PCK_FROM_CLIENT);
//			PacketPool.free(readBufPool.put(pck));
			readBufPool.put(pck);
		}
		// See source code this class
		//ReferenceCountUtil.release(msg); 
	}

	@SuppressWarnings("null")
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// find free proxy bot
		for (BotContext bot: proxyBots) {
			if (bot.setStatus(BotContext.CONN_STATUS)) {
				this.botContext = bot;
				break;
			}
		}
		if (botContext == null) {
			throw new RuntimeException("Can't find proxy bot to connect");
		}
//		System.out.println("Client channel active stage=" + stage);
		botContext.getClientConnections().get(stage).setHandlerContext(ctx);
		botContext.setConnectStage(stage);

        this.readBufPool = botContext.getReadBuffer();
		final CryptorPlugin cp = botContext.getCryptorPlugin();
		if (cp != null)
			cp.onConnected(ScriptPlugin.CONN_TO_CLIENT);
		final ScriptPlugin script = botContext.getScript();
		long startTime = System.currentTimeMillis();
		if (script != null ) {
			script.onConnected(ScriptPlugin.CONN_TO_CLIENT);
			long endTime = System.currentTimeMillis();
			if (endTime - startTime > ScriptPlugin.MAX_WORK_TIME)
				logger.error("Script method onConnected with name "+script.getName() + " works " + (endTime - startTime) + "ms");
		}
		botContext.getServerConnections().get(stage).connect(
				botContext.getBotEnvironment().getServerAddresses().get(stage));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		botContext.getClientConnections().get(stage).setHandlerContext(null);
		final CryptorPlugin cp = botContext.getCryptorPlugin();
		if (cp != null)
			cp.onDisconnected(ScriptPlugin.DISCONN_FROM_CLIENT);
		final ScriptPlugin script = botContext.getScript();
		long startTime = System.currentTimeMillis();
		if (script != null ) {
			script.onDisconnected(ScriptPlugin.DISCONN_FROM_CLIENT);
			long endTime = System.currentTimeMillis();
			if (endTime - startTime > ScriptPlugin.MAX_WORK_TIME)
				logger.error("Script method onDisconnected with name "+script.getName() + " works " + (endTime - startTime) + "ms");
		}
		if (botContext.getServerConnections().get(stage).getDisconnectionPermit())
			botContext.getServerConnections().get(stage).disconnect();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
//		super.exceptionCaught(ctx, cause);
	}
}