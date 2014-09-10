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
package com.xiovr.unibot.bot.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.packet.Packet;

public interface BotConnection {

	public void init(@NonNull NioEventLoopGroup workerGroup, 
			@NonNull BotContext botContext, int stage);

	public int getStage();
	public void setHandlerContext(ChannelHandlerContext ctx);
	public ChannelHandlerContext getHandlerContext();
	public boolean isConnected();
	public void connect(@NonNull InetSocketAddress address);
	public void disconnect();
	public boolean getDisconnectionPermit();
	public void setDisconnectionPermit(boolean bPermit);
	
	public void write(Packet pck);
	public void flush();
	public void writeAndFlush(Packet pck);
}
