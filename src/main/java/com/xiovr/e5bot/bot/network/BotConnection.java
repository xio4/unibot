package com.xiovr.e5bot.bot.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.plugin.CryptorCommand;

public interface BotConnection {

	public void init(@NonNull NioEventLoopGroup workerGroup, 
			@NonNull BotContext botContext, int stage);

	public int getStage();
	public void setHandlerContext(ChannelHandlerContext ctx);
	public ChannelHandlerContext getHandlerContext();
	public boolean isConnected();
	public void connect(@NonNull InetSocketAddress address);
	public void disconnect();
	
	public void write(Packet pck);
	public void flush();
	public void writeAndFlush(Packet pck);
}
