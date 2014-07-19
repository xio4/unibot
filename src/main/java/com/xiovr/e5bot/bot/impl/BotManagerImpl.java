package com.xiovr.e5bot.bot.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiovr.e5bot.bot.Bot;
import com.xiovr.e5bot.bot.BotManager;
import com.xiovr.e5bot.bot.network.BotConnection;

public class BotManagerImpl implements BotManager {

	BotConnection botConnection;
	

	@Override
	public BotConnection getBotConnection() {
		return botConnection;
	}

	@Override
	public void setBotConnection(BotConnection botConnection) {
		this.botConnection = botConnection;
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Bot createBot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int botsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void destroyBot(Integer botId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Bot getBot(int botId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(int botId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop(int botId) {
		// TODO Auto-generated method stub
		
	}

}
