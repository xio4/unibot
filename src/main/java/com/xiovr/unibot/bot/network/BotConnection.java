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
