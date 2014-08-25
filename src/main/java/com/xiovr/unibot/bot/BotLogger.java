package com.xiovr.unibot.bot;

import com.xiovr.unibot.bot.packet.Packet;

public interface BotLogger {

	public void pckLog(Packet packet);
	public void pckModifLog(Packet packet);
}
