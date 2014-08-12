package com.xiovr.e5bot.bot.network;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotEnvironment;

public interface ConnectionFactory {

	public void createProxyConnections(
			@NonNull List<BotContext> proxyBots);

	public void createBotConnectionServer(@NonNull BotContext botContext);

	void dispose();

	public void createBotConnectionClient(@NonNull BotContext botContext);

	public void init(@NonNull BotEnvironment botEnvironment);


}
