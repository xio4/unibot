package com.xiovr.e5bot.bot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xiovr.e5bot.bot.impl.BotEnvironmentImpl;
import com.xiovr.e5bot.bot.impl.BotManagerImpl;
import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.bot.network.impl.BotConnectionImpl;

@Configuration
public class BotConfig {

	@Bean
	public BotEnvironment getBotEnvironment()
	{
		BotEnvironment be = new BotEnvironmentImpl();
		return be;
	}
	
	@Bean
	public BotManager getBotManager()
	{
		BotManager bm = new BotManagerImpl();
		bm.setBotConnection(getBotConnection());
		return bm;
	}
	
	@Bean
	public BotConnection getBotConnection()
	{
		BotConnection bc = new BotConnectionImpl();
		return bc;
	}
}
