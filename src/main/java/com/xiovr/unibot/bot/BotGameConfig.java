package com.xiovr.unibot.bot;

public interface BotGameConfig {
//	public static final String ENVIRONMENT_CFG_FN = "environment.cfg";
//	public static final String SETTINGS_CFG_DIR = "bot_settings";
//	public static final String DEFAULT_CLIENT_IP = "127.0.0.1";
//	public static final int DEFAULT_CLIENT_PORT = 2308;
//	public static final String DEFAULT_SERVER_IP = "127.0.0.1";
//	public static final int DEFAULT_SERVER_PORT = 2308;
//	public static final int PORT_RANGE_MIN = 25000;
//	public static final int PORT_RANGE_MAX = 25500;
	
	
	public static final String VERSION = "0.1";
	public static final int DEFAULT_PORT = 6666;
	public static final String DEFAULT_HOSTNAME = "127.0.0.1";

//	public void saveBotSettings(BotSettings settings, String botFn, String comments);

//	public Properties createBotSettings(String botFn, String comments);

//	public void loadBotSettings(BotSettings botSettings, String botFn);

//	public void savePropsFromBotEnvironment(BotEnvironment botEnvironment);

//	public void loadPropsToBotEnvironment(BotEnvironment botEnvironment);

//	public Properties createSettings(Class<?> clazz, String fn, String comment);

	public void loadSettings(Settings instance, String fn);

	public void saveSettings(Settings instance, String fn, String comment);

}
