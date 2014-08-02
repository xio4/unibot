package com.xiovr.e5bot.bot.network.impl;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.plugin.CryptorCommand;

public abstract class AbstractBotConnection implements BotConnection{

	@Override
	public void setBotContext(@NonNull BotContext botContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BotContext getBotContext() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public abstract void onRead();

}
