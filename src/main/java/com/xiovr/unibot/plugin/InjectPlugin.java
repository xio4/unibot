package com.xiovr.unibot.plugin;

import com.xiovr.unibot.bot.BotContext;

public interface InjectPlugin {

	public void init(BotContext botContext);

	public void dispose();
}
