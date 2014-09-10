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
