package com.xiovr.e5bot.bot.network;

import com.xiovr.e5bot.bot.BotEnvironment;

public interface ClientConnectionRunnable extends Runnable {

	public void setBotEnvironment(BotEnvironment botEnvironment);

	public BotEnvironment getBotEnvironment();


}
