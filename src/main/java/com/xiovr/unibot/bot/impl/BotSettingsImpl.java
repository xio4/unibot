/**
 * Copyright (c) 2014 xio4
 * Universal bot for lineage-like games (Archeage, Lineage2 etc)
 *
 * This file is part of Unibot.
 *
 * Unibot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.xiovr.unibot.bot.impl;

import com.xiovr.unibot.bot.BotSettings;

public class BotSettingsImpl implements BotSettings {

	private int botType;
	private String login;
	private String password;
	private String name;
	private int serverId;
	private boolean bAutoConnect;
	private int autoConnectInterval;
	private boolean bDisabled;
	private boolean bLogging;
	private boolean bModifLogging;
	private String scriptPath;
	private int charId;
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
	public boolean getAutoConnect() {
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
	public boolean getDisabled() {
		return this.bDisabled;
	}
	@Override
	public void setDisabled(boolean bDisabled) {
		this.bDisabled = bDisabled;
	}
	@Override
	public boolean getLogging() {
		return this.bLogging;
	}
	@Override
	public void setLogging(boolean bLogging) {
		this.bLogging = bLogging;
	}
	@Override
	public boolean getModifLogging() {
		return this.bModifLogging;
	}
	@Override
	public void setModifLogging(boolean bLogging) {
		this.bModifLogging = bLogging;
		
	}
	@Override
	public void setScriptPath(String path) {
		this.scriptPath = path;
		
	}
	@Override
	public String getScriptPath() {
		return this.scriptPath;
	}
	@Override
	public int getCharId() {
		return this.charId;
	}
	@Override
	public void setCharId(int charId) {
		this.charId = charId;
		
	}

}
