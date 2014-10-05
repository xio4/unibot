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

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.network.BotConnection;
import com.xiovr.unibot.bot.packet.Packet;

public class BotConnectionClientImpl implements BotConnection {
	private int stage;
	private volatile boolean bDisconnectPermit;

	private volatile ChannelHandlerContext ctx;

	public BotConnectionClientImpl() {
		ctx = null;
		bDisconnectPermit = true;
	}
	@Override
	public void connect(@NonNull InetSocketAddress address) {
		//This method not works in client connection
	}

	@Override
	public void disconnect() {
		if (ctx == null) {
			return;
		}
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
		else {
			System.out.println("Client ctx is null!");
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
