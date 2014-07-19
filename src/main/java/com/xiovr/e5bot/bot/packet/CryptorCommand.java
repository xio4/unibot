package com.xiovr.e5bot.bot.packet;

import com.xiovr.e5bot.plugin.PluginContext;

public interface CryptorCommand {
	public void execute(PluginContext context, Packet packet);
	public int getId();
	public void setId(int Id);
}
