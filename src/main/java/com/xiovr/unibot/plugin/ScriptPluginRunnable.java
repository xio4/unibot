package com.xiovr.unibot.plugin;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.RingBufferPool;

public interface ScriptPluginRunnable extends Runnable {
	public void setScript(@NonNull ScriptPlugin script);
}
