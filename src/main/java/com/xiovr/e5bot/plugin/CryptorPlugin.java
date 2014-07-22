package com.xiovr.e5bot.plugin;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.PacketRingBuffer;

public interface CryptorPlugin {
	public void init(BotContext context);
	/**
	 * @param command If this null then createFirstCommand
	 * @return
	 */
	//ArrayBlockingQueue bq;
	public CryptorCommand getNextCommand(CryptorCommand lastCommand);
	public void update();
	public void decryptFromServer(Packet enc, Packet dec);
	public void decryptFromClient(Packet enc, Packet dec);
	public void encryptToServer(Packet dec, Packet enc);
	public void encryptToClient(Packet dec, Packet enc);
	public void dispose();
}
