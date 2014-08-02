package com.xiovr.e5bot.bot.network;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.plugin.CryptorCommand;

public interface BotConnection {

	public void setBotContext(@NonNull BotContext botContext);
	public BotContext getBotContext();

	@Deprecated
	public void write(Packet pck);
	//Abstract method
	public void onRead();
}
