package com.xiovr.unibot.bot;

public interface BotSettings extends Settings {
	public static final int INGAME_TYPE = 0x01;
	public static final int OUTGAME_TYPE = 0x02;
	public static final int PROXY_TYPE = 0x03;


	public int getType();
	@Param(name="bot.type", values= { "2" })
	public void setType(int type);
	public String getLogin();
	@Param(name="bot.login", values = { "" } )
	public void setLogin(String login);
	public String getPassword();
	@Param(name="bot.password", values= { "" })
	public void setPassword(String password);
	public String getName();
	@Param(name="bot.name" , values = "")
	public void setName(String name);
	public int getCharId();
	@Param(name="bot.char_id" , values = "0")
	public void setCharId(int charId);
	public int getServerId();
	@Param(name = "bot.server_id", values = "0")
	public void setServerId(int id);
	@Param(name = "bot.script_path", values = "")
	public void setScriptPath(String path);
	public String getScriptPath();
	
	public boolean getAutoConnect();
	@Param(name = "bot.autoconnect", values= "false")
	public void setAutoConnect(boolean bConnect);
	public int getAutoConnectInterval();
	@Param(name = "bot.autoconnect_interval", values = "10")
	public void setAutoConnectInterval(int connectInterval);
	public boolean getDisabled();
	@Param(name = "bot.disabled", values = "false")
	public void setDisabled(boolean bDisabled);
	public boolean getLogging();
	@Param(name = "bot.logging", values = "false")
	public void setLogging(boolean bLogging);
	public boolean getModifLogging();
	@Param(name = "bot.modif_logging", values = "false")
	public void setModifLogging(boolean bLogging);

}
