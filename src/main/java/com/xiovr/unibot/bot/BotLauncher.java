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

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.xiovr.unibot.bot.network.ConnectionFactory;
import com.xiovr.unibot.plugin.PluginLoader;
import com.xiovr.unibot.utils.exceptions.BotDoNotExistsException;
import com.xiovr.unibot.utils.exceptions.BotScriptCannotStopException;

/**
 * @author xio4 E5bot launcher bean
 */
@Component
public class BotLauncher implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(BotLauncher.class);

	@Autowired 
	BotGameConfig botGameConfig;
	@Autowired
	BotEnvironment botEnvironment;
	@Autowired 
	BotManager botManager;
	@Autowired
	PluginLoader pluginLoader;
	@Autowired
	ConnectionFactory connectionFactory;

	@SuppressWarnings("null")
	public void run(String... args) {

		
//		botGameConfig.loadSettings(botEnvironment, dir + "/" + BotEnvironment.ENVIRONMENT_CFG_FN);
		botGameConfig.loadSettings(botEnvironment, "/tmp/" + BotEnvironment.ENVIRONMENT_CFG_FN);
		// Check prefixscriptpath exists
		File fn = new File(botGameConfig.getAbsDirPath() + "/" + botEnvironment.getScriptsPathPrefix());
		if (!fn.isDirectory()) {
			if (!fn.mkdir()) {
				logger.error("Cannot create directory with name {}" , botEnvironment.getScriptsPathPrefix());
				return;
			}
		}
		File fn2 = new File(botGameConfig.getAbsDirPath() + "/" + BotSettings.PATH_PREFIX);
		if (!fn2.isDirectory()) {
			if (!fn2.mkdir()) {
				logger.error("Cannot create directory with name {}" , BotSettings.PATH_PREFIX);
				return;
			}
		}
		connectionFactory.init(botEnvironment);
		botManager.setBotEnvironment(botEnvironment);
		botManager.setBotGameConfig(botGameConfig);
		// Dir for tests in eclipse

		try {
//			pluginLoader.loadCryptorPlugin("/" + dir + "../bsfg_cryptor.jar");
			pluginLoader.loadCryptorPlugin("/tmp/bsfg_cryptor.jar");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		botManager.setPluginLoader(pluginLoader);
		botManager.setConnectionFactory(connectionFactory);
		connectionFactory.createProxyListeners(botManager.getBots(BotSettings.PROXY_TYPE));
		BotContext bot = null;
//		Thread.currentThread().setContextClassLoader();
		try {
			botManager.createBot(BotSettings.PROXY_TYPE, "/tmp/test1.cfg");
			bot = botManager.createBot(BotSettings.OUTGAME_TYPE, "/tmp/test1.cfg");

//			botManager.connect(bot.getBotId(), bot.getBotSettings().getType());
		} catch (BotDoNotExistsException | BotScriptCannotStopException e) {
			e.printStackTrace();
		}

	}	
}
