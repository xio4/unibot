package com.xiovr.e5bot.bot;

import java.util.Properties;

public interface BotGameConfig {
	public static final String VERSION = "0.1";
	public static final String ENVIRONMENT_CFG_FN = "environment.cfg";
	public static final String DEFAULT_CLIENT_IP = "127.0.0.1";
	public static final int DEFAULT_CLIENT_PORT = 2308;
	public static final String DEFAULT_SERVER_IP = "127.0.0.1";
	public static final int DEFAULT_SERVER_PORT = 2308;

	void saveBotSettings(BotSettings settings, String botFn, String comments);

	Properties createBotSettings(String botFn, String comments);

	void loadBotSettings(BotSettings botSettings, String botFn);

	void savePropsFromBotEnvironment(BotEnvironment botEnvironment);

	void loadPropsToBotEnvironment(BotEnvironment botEnvironment);

}
