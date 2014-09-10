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

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import com.xiovr.unibot.bot.network.ConnectionDecoder;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.PacketPool;

public class ConnectionDecoderImpl extends ByteToMessageDecoder implements
		ConnectionDecoder

{

	public ConnectionDecoderImpl() {
		super();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// System.out.println("Run decoder bytes="+ in.readableBytes());

		for (;;) {
			if (in.readableBytes() < 2)
				return;

			// System.out.println("Decoder size=" + in.readableBytes());
			in.markReaderIndex();

			// int dataLen = in.readUnsignedShort() - 2;
			byte b1 = in.readByte();
			byte b2 = in.readByte();
			int dataLen = (b1 & 0xFF) | ((b2 << 8) & 0xFF00);

			// System.out.println("packet len =" + dataLen);

			// System.out.println("readable bytes =" + in.readableBytes());
			if (in.readableBytes() < dataLen - 2) {
				in.resetReaderIndex();
				return;
			}
			Packet pck = PacketPool.obtain();
			pck.clear();

			byte[] pckArr = pck.getBuf().array();
			// byte[] inArr = in.array();
			in.readBytes(pckArr, 2, dataLen - 2);
			pck.putHeader(dataLen);
			pck.setPosition(dataLen);
			// int rIndex = in.readerIndex();
			// System.arraycopy(inArr, rIndex-2, pckArr, 0, dataLen+2);
			// in.readerIndex(rIndex+dataLen);
			out.add(pck);
			// System.out.println("DECODER END");
		}
	}

}
