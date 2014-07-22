package com.xiovr.e5bot.bot.impl;

import com.xiovr.e5bot.bot.Bot;

public class BotImpl implements Bot {

	private int botId;
	private int botStatus;
	private int botType;
	public BotImpl()
	{
		botId = -1;
		botStatus = Bot.OFFLINE_STATUS;
		botType = Bot.INGAME_TYPE;
	}
	@Override
	public int getBotId() {
		return botId;
	}

	@Override
	public void setBotId(int botId) {
		
	}
	@Override
	public void setStatus(int status) {
		botStatus = status;
		
	}
	@Override
	public int getStatus() {
		return botStatus;
	}
	@Override
	public int getType() {
		return botType;
	}
	@Override
	public void setType(int type) {
		botType = type;
		
	}
	@Override
	public String getLogin() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLogin(String login) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getServerId() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setServerId(int id) {
		// TODO Auto-generated method stub
		
	}

}
