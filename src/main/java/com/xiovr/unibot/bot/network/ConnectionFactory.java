package com.xiovr.unibot.bot.network;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;

public interface ConnectionFactory {

	public void createProxyListeners(
			@NonNull List<BotContext> proxyBots);

	public void createBotConnectionServer(@NonNull BotContext botContext);

	void dispose();

	public void createBotConnectionClient(@NonNull BotContext botContext);

	public void init(@NonNull BotEnvironment botEnvironment);


}
