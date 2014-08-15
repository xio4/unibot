package com.xiovr.e5bot.bot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.xiovr.e5bot.utils.BotUtils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.xiovr.e5bot.utils.BotUtils;

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

	public void run(String... args) {
//		botGameConfig.loadPropsToBotEnvironment(botEnvironment);
		botGameConfig.loadSettings(botEnvironment, BotEnvironment.ENVIRONMENT_CFG_FN);
	}	
}
