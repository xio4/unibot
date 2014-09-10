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

public interface BotGameConfig {
//	public static final String ENVIRONMENT_CFG_FN = "environment.cfg";
//	public static final String SETTINGS_CFG_DIR = "bot_settings";
//	public static final String DEFAULT_CLIENT_IP = "127.0.0.1";
//	public static final int DEFAULT_CLIENT_PORT = 2308;
//	public static final String DEFAULT_SERVER_IP = "127.0.0.1";
//	public static final int DEFAULT_SERVER_PORT = 2308;
//	public static final int PORT_RANGE_MIN = 25000;
//	public static final int PORT_RANGE_MAX = 25500;
	
	
	public static final String VERSION = "0.1";
	public static final int DEFAULT_PORT = 6666;
	public static final String DEFAULT_HOSTNAME = "127.0.0.1";

//	public void saveBotSettings(BotSettings settings, String botFn, String comments);

//	public Properties createBotSettings(String botFn, String comments);

//	public void loadBotSettings(BotSettings botSettings, String botFn);

//	public void savePropsFromBotEnvironment(BotEnvironment botEnvironment);

//	public void loadPropsToBotEnvironment(BotEnvironment botEnvironment);

//	public Properties createSettings(Class<?> clazz, String fn, String comment);

	public void loadSettings(Settings instance, String fn);

	public void saveSettings(Settings instance, String fn, String comment);

}
