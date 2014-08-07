package com.xiovr.e5bot.bot.network;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotEnvironment;

public interface ConnectionContext {

	public void init(@NonNull BotEnvironment botEnvironment);
}
