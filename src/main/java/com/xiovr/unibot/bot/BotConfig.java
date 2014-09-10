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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xiovr.unibot.bot.impl.BotEnvironmentImpl;
import com.xiovr.unibot.bot.impl.BotGameConfigImpl;
import com.xiovr.unibot.bot.impl.BotManagerImpl;
import com.xiovr.unibot.bot.network.ConnectionFactory;
import com.xiovr.unibot.bot.network.impl.ConnectionFactoryImpl;
import com.xiovr.unibot.plugin.PluginLoader;
import com.xiovr.unibot.plugin.impl.PluginLoaderImpl;

@Configuration
public class BotConfig {

	@Bean
	public BotManager getBotManager()
	{
		BotManager bm = new BotManagerImpl();
		return bm;
	}
	
	@Bean(name="botGameConfig")
	public BotGameConfig getBotGameConfig() 
	{
		BotGameConfig bgc = new BotGameConfigImpl();
		return bgc;
	}
	
	@Bean(name="botEnvironment")
	public BotEnvironment getBotEnvironment()
	{
		BotEnvironment be = new BotEnvironmentImpl();
		return be;
	}
	
	@Bean(name="pluginLoader")
	public PluginLoader getPluginLoader()
	{
		PluginLoader pl = new PluginLoaderImpl();
		return pl;
	}
	
	@Bean(name="connectionFactory")
	public ConnectionFactory getConnectionFactory()
	{
		ConnectionFactory cf = new ConnectionFactoryImpl();
		return cf;
	}
	
}
