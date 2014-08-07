package com.xiovr.e5bot.plugin;

import com.xiovr.e5bot.bot.packet.Packet;

public interface CryptorCommand {
	public void execute(Packet encPck, Packet decPck);
	public int getId();
	public void setId(int Id);
}
