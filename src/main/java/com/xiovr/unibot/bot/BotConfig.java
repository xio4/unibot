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
