package com.xiovr.unibot.plugin;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.packet.Packet;

public interface CryptorPlugin {

	public void init(BotContext context);
	/**
	 * @param command If this null then createFirstCommand
	 * @return
	 */
	//ArrayBlockingQueue bq;
	public void execClientCommand(@NonNull Packet enc, @NonNull Packet dec);
	public void execServerCommand(@NonNull Packet enc, @NonNull Packet dec);
	// TODO Not implemented yet
	public void update();
	public Packet decryptFromServer(@NonNull Packet enc, @NonNull Packet dec);
	public Packet decryptFromClient(@NonNull Packet enc, @NonNull Packet dec);
	public Packet encryptToServer(@NonNull Packet dec,@NonNull  Packet enc);
	public Packet encryptToClient(@NonNull Packet dec,@NonNull  Packet enc);
	public void dispose();
	public void onConnected(int type);
	public void onDisconnected(int type);
	public void modifyServerPacket(Packet pck2);
	public void modifyClientPacket(Packet pck2);
}
