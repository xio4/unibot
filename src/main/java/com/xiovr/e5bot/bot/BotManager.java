package com.xiovr.e5bot.bot;

import com.xiovr.e5bot.bot.analize.AnalizeManager;
import com.xiovr.e5bot.bot.network.BotConnection;

public interface BotManager {
	public BotConnection getBotConnection();
	public void setBotConnection(BotConnection botConnection);
	public void clear();
	public Bot createBot();
	public void destroyBot(Integer botId);
	public Bot getBot(int botId);
	public int botsCount();
	public void start(int botId);
	public void stop(int botId);

}
