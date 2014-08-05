package com.xiovr.e5bot.bot;

public interface BotSettings {
	public static final int INGAME_TYPE = 0x01;
	public static final int OUTGAME_TYPE = 0x02;
	public static final int PROXY_TYPE = 0x03;


	public int getType();
	public void setType(int type);
	public String getLogin();
	public void setLogin(String login);
	public String getPassword();
	public void setPassword(String password);
	public String getName();
	public void setName(String name);
	public int getServerId();
	public void setServerId(int id);
	
	public boolean isAutoConnect();
	public void setAutoConnect(boolean bConnect);
	public int getAutoConnectInterval();
	public void setAutoConnectInterval(int connectInterval);
	public boolean isDisabled();
	public void setDisabled(boolean bDisabled);
	public boolean isLogging();
	public void setLogging(boolean bLogging);
	public boolean isModifLogging();
	public void setModifLogging(boolean bLogging);

}
