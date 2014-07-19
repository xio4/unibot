package com.xiovr.e5bot.bot;

public interface Bot {

	public static final int OFFLINE_STATUS = 0x00;
	public static final int LOGIN_STATUS = 0x01;
	public static final int INWORLD_STATUS = 0x02;

	public static final int INGAME_TYPE = 0x01;
	public static final int OUTGAME_TYPE = 0x02;

	public int getBotId();
	public void setBotId(int botId);
	public void setStatus(int status);
	public int getStatus();
	public int getType();
	public void setType(int type);

}
