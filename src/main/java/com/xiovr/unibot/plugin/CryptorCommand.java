package com.xiovr.unibot.plugin;

import com.xiovr.unibot.bot.packet.Packet;

public interface CryptorCommand {
	public void execute(Packet encPck, Packet decPck);
	public int getId();
	public void setId(int Id);
}
