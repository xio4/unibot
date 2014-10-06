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
package com.xiovr.unibot.bot;

public interface BotSettings extends Settings {
	public static final int INGAME_TYPE = 0x01;
	public static final int OUTGAME_TYPE = 0x02;
	public static final int PROXY_TYPE = 0x03;
	public static final String PATH_PREFIX="bot_config";


	public int getType();
	@Param(name="bot.type", values= { "3" })
	public void setType(int type);
	public String getLogin();
	@Param(name="bot.login", values = { "testtesttest" } )
	public void setLogin(String login);
	public String getPassword();
	@Param(name="bot.password", values= { "testtest" })
	public void setPassword(String password);
	public String getName();
	@Param(name="bot.name" , values = "adda")
	public void setName(String name);
	public int getCharId();
	@Param(name="bot.char_id" , values = "0")
	public void setCharId(int charId);
	public int getServerId();
	@Param(name = "bot.server_id", values = "01")
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
