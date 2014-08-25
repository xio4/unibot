package com.xiovr.unibot.bot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xiovr.unibot.bot.impl.BotEnvironmentImpl;
import com.xiovr.unibot.bot.impl.BotManagerImpl;
import com.xiovr.unibot.bot.network.BotConnection;

@Configuration
public class BotConfig {

	@Bean
	public BotManager getBotManager()
	{
		BotManager bm = new BotManagerImpl();
		return bm;
	}
	
}
