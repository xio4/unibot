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

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.network.ConnectionFactory;
import com.xiovr.unibot.plugin.PluginLoader;
import com.xiovr.unibot.utils.exceptions.BotDoNotExistsException;
import com.xiovr.unibot.utils.exceptions.BotScriptCannotStopException;

public interface BotManager {
	public static final int BOT_MAX_COUNT = 1000;

	public BotEnvironment getBotEnvironment();
	public void setBotEnvironment(@NonNull BotEnvironment botEnvironment);
	public ConnectionFactory getConnectionFactory();
	public void setConnectionFactory(@NonNull ConnectionFactory connContext);
	public void setBotGameConfig(@NonNull BotGameConfig botGameConfig);
	public BotGameConfig getBotGameConfig();
	public void setBotLogger(@NonNull BotLogger botLogger);
	public BotLogger getBotLogger();
	public void clear();
	public BotContext createBot(int botType, @NonNull String configName) throws BotDoNotExistsException, BotScriptCannotStopException;
	// Reset only botContext, botConnection isn't reset!
	public void resetBot(int botId, int botType) throws BotDoNotExistsException, BotScriptCannotStopException;
	public void destroyBot(int botId, int botType) throws BotDoNotExistsException;
	public BotContext getBot(int botId, int botType) throws BotDoNotExistsException;
	public void loadScript(int botId, int botType, @NonNull String scriptPath) throws Exception;
	public void removeScript(int botId, int botType) throws BotDoNotExistsException;
	@Deprecated
	public void loadBots();
	public List<BotContext> getBots(int botType);
	public int botsCount(int botType) throws BotDoNotExistsException;
	public void connect(int botId, int botType) throws BotDoNotExistsException;
	public void disconnect(int botId, int botType) throws BotDoNotExistsException;
	public BotMessageTransferRunnable getBotMessageTransfer();
	public void setBotMessageTransfer(@NonNull BotMessageTransferRunnable bmtt);
	public void setPluginLoader(@NonNull PluginLoader pluginLoader);
	public PluginLoader getPluginLoader();

}
