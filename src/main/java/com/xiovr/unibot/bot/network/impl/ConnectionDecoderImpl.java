package com.xiovr.unibot.bot.network.impl;

import java.nio.ByteOrder;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.network.ConnectionDecoder;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.PacketPool;
import com.xiovr.unibot.bot.packet.impl.PacketImpl;

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

//		int dataLen = in.readUnsignedShort() - 2;
		byte b1 = in.readByte();
		byte b2 = in.readByte();
		int dataLen = (b1 & 0xFF) | ((b2 << 8) & 0xFF);

//		System.out.println("packet len =" + dataLen);

//		System.out.println("readable bytes =" + in.readableBytes());
		if (in.readableBytes() < dataLen - 2) {
			in.resetReaderIndex();
			return;
		}
		Packet pck = PacketPool.obtain();
		pck.clear();

		byte[] pckArr = pck.getBuf().array();
//		byte[] inArr = in.array();
		in.readBytes(pckArr, 2, dataLen - 2);
		pck.putHeader(dataLen);
		pck.setPosition(dataLen);
//		int rIndex = in.readerIndex();
//		System.arraycopy(inArr, rIndex-2, pckArr, 0, dataLen+2);
//		in.readerIndex(rIndex+dataLen);
		out.add(pck);
//		System.out.println("DECODER END");
	}

}
