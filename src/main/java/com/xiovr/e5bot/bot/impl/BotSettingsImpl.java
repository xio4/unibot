package com.xiovr.e5bot.bot.impl;

import com.xiovr.e5bot.bot.BotSettings;

public class BotSettingsImpl implements BotSettings {

	private int botType;
	private String login;
	private String password;
	private String name;
	private int serverId;
	private boolean bClientProxy;
	private boolean bAutoConnect;
	private int autoConnectInterval;
	private boolean bDisabled;
	private boolean bLogging;
	public BotSettingsImpl()
	{
		botType = BotSettings.INGAME_TYPE;
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
		return this.login;
	}
	@Override
	public void setLogin(String login) {
		this.login = login;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public void setPassword(String password) {
		this.password = password;
		
	}
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int getServerId() {
		return this.serverId;
	}
	@Override
	public void setServerId(int id) {
		this.serverId = id;
		
	}
	@Override
	public boolean isClientProxy() {
		return bClientProxy;
	}
	@Override
	public void setClientProxy(boolean bProxy) {
		this.bClientProxy = bProxy;
	}
	@Override
	public boolean isAutoConnect() {
		return this.bAutoConnect;
	}
	@Override
	public void setAutoConnect(boolean bConnect) {
		this.bAutoConnect = bConnect;
	}
	@Override
	public int getAutoConnectInterval() {
		return this.autoConnectInterval;
	}
	@Override
	public void setAutoConnectInterval(int connectInterval) {
		this.autoConnectInterval = connectInterval;
		
	}
	@Override
	public boolean isDisabled() {
		return this.bDisabled;
	}
	@Override
	public void setDisabled(boolean bDisabled) {
		this.bDisabled = bDisabled;
	}
	@Override
	public boolean isLogging() {
		return this.bLogging;
	}
	@Override
	public void setLogging(boolean bLogging) {
		this.bLogging = bLogging;
	}

}