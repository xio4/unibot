package com.xiovr.e5bot.bot.network.impl;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import com.xiovr.e5bot.bot.network.ConnectionEncoder;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.PacketPool;

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