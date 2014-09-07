package com.xiovr.unibot.plugin;

import com.xiovr.unibot.bot.packet.Packet;

public interface CryptorCommand {
	public void execute(Packet encPck, Packet decPck);
	@Deprecated
	public int getId();
	@Deprecated
	public void setId(int Id);
}
