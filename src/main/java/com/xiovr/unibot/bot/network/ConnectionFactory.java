package com.xiovr.unibot.bot.network;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;

public interface ConnectionFactory {

	public void createProxyListeners(
			@NonNull List<BotContext> proxyBots);


	void dispose();


	public void init(@NonNull BotEnvironment botEnvironment);

	void createBotConnectionServer(@NonNull BotContext botContext, int stage);

	void createBotConnectionClient(@NonNull BotContext botContext, int stage);


}
