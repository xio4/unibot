package com.xiovr.e5bot.bot;

import com.xiovr.e5bot.plugin.BotContext;

public interface BotThread extends Runnable {
	public static final int START_CMD = 0x00;
	public static final int STOP_CMD = 0x01;

	public void setContext(BotContext context);
	public BotContext getContext();
	public int getCmd();
	public void setCmd(int cmd);


}
