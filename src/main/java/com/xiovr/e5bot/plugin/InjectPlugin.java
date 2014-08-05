package com.xiovr.e5bot.plugin;

import com.xiovr.e5bot.bot.BotContext;

public interface InjectPlugin {

	public void init(BotContext botContext);

	public void dispose();
}
