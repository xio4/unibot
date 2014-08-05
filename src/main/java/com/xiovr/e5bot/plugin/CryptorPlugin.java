package com.xiovr.e5bot.plugin;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.packet.Packet;

public interface CryptorPlugin {

	public void init(BotContext context);
	/**
	 * @param command If this null then createFirstCommand
	 * @return
	 */
	//ArrayBlockingQueue bq;
	public CryptorCommand getNextCommand(CryptorCommand lastCommand);
	// TODO Not implemented yet
	public void update();
	public Packet decryptFromServer(@NonNull Packet enc, @NonNull Packet dec);
	public Packet decryptFromClient(@NonNull Packet enc, @NonNull Packet dec);
	public Packet encryptToServer(@NonNull Packet dec,@NonNull  Packet enc);
	public Packet encryptToClient(@NonNull Packet dec,@NonNull  Packet enc);
	public void dispose();
	public void onConnected(int type);
	public void onDisconnected(int type);
}
