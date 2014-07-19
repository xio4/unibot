package com.xiovr.e5bot.plugin;

import com.xiovr.e5bot.bot.packet.CryptorCommand;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.PacketRingBuffer;

public interface CryptorPlugin {
	public void init(PluginContext context);
	public CryptorCommand setNextCommand(CryptorCommand command);
	public void update();
	public void decryptFromServer(PacketRingBuffer buf, Packet pck);
	public void decryptFromClient(PacketRingBuffer buf, Packet pck);
	public void encryptToServer(PacketRingBuffer buf, Packet pck);
	public void encryptToClient(PacketRingBuffer buf, Packet pck);
	public void dispose();
}
