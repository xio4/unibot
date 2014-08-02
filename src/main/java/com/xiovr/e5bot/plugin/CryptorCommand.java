package com.xiovr.e5bot.plugin;

import com.xiovr.e5bot.bot.packet.Packet;

public interface CryptorCommand {
	public void execute(Packet pck);
	public int getId();
	public void setId(int Id);
}
