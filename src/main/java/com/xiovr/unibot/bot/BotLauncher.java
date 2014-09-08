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

		String dir = new File("").getAbsolutePath();
		
		botGameConfig.loadSettings(botEnvironment, dir + "/" + BotEnvironment.ENVIRONMENT_CFG_FN);
		connectionFactory.init(botEnvironment);
		botManager.setBotEnvironment(botEnvironment);
		botManager.setBotGameConfig(botGameConfig);
//		String dir = getClass().getProtectionDomain().getCodeSource()
//				.getLocation().toString().substring(6);
		logger.info("DIR:" + dir);
		System.out.println("DIR:" + dir);
		try {
			pluginLoader.loadCryptorPlugin(dir + "/bsfg_cryptor.jar");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		botManager.setPluginLoader(pluginLoader);
		botManager.setConnectionFactory(connectionFactory);
		connectionFactory.createProxyListeners(botManager.getBots(BotSettings.PROXY_TYPE));
//		Thread.currentThread().setContextClassLoader();
		try {
			botManager.createBot(BotSettings.PROXY_TYPE, dir + "/test1.cfg");
		} catch (BotDoNotExistsException e) {
			e.printStackTrace();
		}
	}	
}
