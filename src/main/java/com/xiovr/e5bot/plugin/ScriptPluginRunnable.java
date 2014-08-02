package com.xiovr.e5bot.plugin;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.RingBufferPool;

public interface ScriptPluginRunnable extends Runnable {
	public void notifyUpdate();
	public void setScript(@NonNull ScriptPlugin script);
}
