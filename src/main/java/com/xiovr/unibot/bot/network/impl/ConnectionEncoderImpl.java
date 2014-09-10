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

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import com.xiovr.unibot.bot.network.ConnectionEncoder;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.PacketPool;

//public class ServerConnectionEncoderImpl extends MessageToByteEncoder<Packet> 
public class ConnectionEncoderImpl extends ChannelOutboundHandlerAdapter 
	implements ConnectionEncoder
{

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {

        if(!(msg instanceof Packet)) {
            ctx.write(msg, promise);
            return;
        }

		final Packet pck = (Packet)msg;
//		System.out.println("Encoded packet pck.len=" + pck.getPosition());

		int size = pck.getPosition();

		ByteBuf encoded = ctx.alloc().heapBuffer(size);

//		final byte[] encArr = encoded.array();
		final byte[] pckArr = pck.getBuf().array();

		encoded.writeBytes(pckArr, 0, size);

//		System.arraycopy(pckArr, 0, encArr, encoded.writerIndex(),size); 

//		encoded.writerIndex(encoded.writerIndex()+pck.getPosition());
		ctx.write(encoded, promise);
		PacketPool.free(pck);
	}

}
