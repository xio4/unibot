package com.xiovr.e5bot.bot.network.impl;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.network.ConnectionDecoder;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.PacketPool;
import com.xiovr.e5bot.bot.packet.impl.PacketImpl;

public class ConnectionDecoderImpl extends ByteToMessageDecoder 
	implements ConnectionDecoder 

{

	public ConnectionDecoderImpl() {
		super();
	}
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		System.out.println("Run decoder bytes="+ in.readableBytes());
		if (in.readableBytes() < 2)
			return;
		
		in.markReaderIndex();
		int pckLen = in.readUnsignedShort() - 2;
		System.out.println("packet len =" + pckLen);
		System.out.println("readable bytes =" + in.readableBytes());
		if (in.readableBytes() < pckLen) {
			in.resetReaderIndex();
			return;
		}
		Packet pck = PacketPool.obtain();

		byte[] pckArr = pck.getBuf().array();
//		byte[] inArr = in.array();
		in.readBytes(pckArr, 2, pckLen);
		pck.putHeader(pckLen+2);
//		int rIndex = in.readerIndex();
//		System.out.println("Here?");
//		System.arraycopy(inArr, rIndex-2, pckArr, 0, pckLen+2);
//		in.readerIndex(rIndex+pckLen);
		out.add(pck);
	}

}
