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
//		System.out.println("Run decoder bytes="+ in.readableBytes());
		if (in.readableBytes() < 2)
			return;
		
//		System.out.println("Decoder size=" + in.readableBytes());
		in.markReaderIndex();
		int dataLen = in.readUnsignedShort() - 2;
//		System.out.println("packet len =" + dataLen);
//		System.out.println("readable bytes =" + in.readableBytes());
		if (in.readableBytes() < dataLen) {
			in.resetReaderIndex();
			return;
		}
		Packet pck = PacketPool.obtain();
		pck.clear();

		byte[] pckArr = pck.getBuf().array();
//		byte[] inArr = in.array();
		in.readBytes(pckArr, 2, dataLen);
		pck.putHeader(dataLen+2);
		pck.setPosition(dataLen+2);
//		int rIndex = in.readerIndex();
//		System.arraycopy(inArr, rIndex-2, pckArr, 0, dataLen+2);
//		in.readerIndex(rIndex+dataLen);
		out.add(pck);
	}

}
