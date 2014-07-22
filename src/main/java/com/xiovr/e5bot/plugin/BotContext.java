package com.xiovr.e5bot.plugin;

import com.xiovr.e5bot.bot.Bot;
import com.xiovr.e5bot.bot.packet.Packet;

public interface BotContext {
	public Bot get();
	public void sendToServer(Packet pck);
	public void sendToClient(Packet pck);
	public boolean sendMsgToBot(String name, String msg);
}
