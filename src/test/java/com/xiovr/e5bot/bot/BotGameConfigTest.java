package com.xiovr.e5bot.bot;

import java.io.File;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.e5bot.bot.impl.BotEnvironmentImpl;
import com.xiovr.e5bot.bot.impl.BotGameConfigImpl;
import com.xiovr.e5bot.bot.impl.BotSettingsImpl;
import com.xiovr.e5bot.TestBase;

/**
 * @author xio4
 *
 */
public class BotGameConfigTest extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(BotGameConfigTest.class);
	@Autowired
	BotEnvironment botEnvironment;
	@Autowired
	BotGameConfig botGameConfig;

	@Test()
	public void testBotEnvironment_load_and_save()
	{
		File fn = new File(((BotGameConfigImpl)botGameConfig).DIR_PATH + "/" + BotGameConfig.ENVIRONMENT_CFG_FN);
		fn.delete();
		botGameConfig.loadPropsToBotEnvironment(botEnvironment);
		Assert.assertEquals(botEnvironment.getNextBotConnectionInterval(), 5);
		Assert.assertEquals(botEnvironment.getUpdateInterval(), 100);
		Assert.assertEquals(botEnvironment.getClientAddresses().get(0).getHostString(), 
				BotGameConfigImpl.DEFAULT_CLIENT_IP);
		Assert.assertEquals(botEnvironment.getClientAddresses().get(0).getPort(), 
				BotGameConfigImpl.DEFAULT_CLIENT_PORT);
		
		Assert.assertEquals(botEnvironment.getServerAddresses().get(0).getHostString(), 
				BotGameConfigImpl.DEFAULT_SERVER_IP);
		Assert.assertEquals(botEnvironment.getServerAddresses().get(0).getPort(),
				BotGameConfigImpl.DEFAULT_SERVER_PORT);
		Assert.assertEquals(botEnvironment.isProxy(), false);
		Assert.assertEquals(botEnvironment.isRawData(), false);
		
		botEnvironment.setNextBotConnectionInterval(10);
		botEnvironment.setUpdateInterval(200);
		botEnvironment.getServerAddresses().clear();
		botEnvironment.getClientAddresses().clear();
		botEnvironment.addClientAddress(new InetSocketAddress("255.255.255.255", 1000));
		botEnvironment.addServerAddress(new InetSocketAddress("125.124.123.11", 2000));
		botEnvironment.addClientAddress(new InetSocketAddress("255.255.255.254", 1001));
		botEnvironment.addServerAddress(new InetSocketAddress("126.124.123.11", 2001));
		botEnvironment.setProxy(true);
		botEnvironment.setRawData(true);
		
		botGameConfig.savePropsFromBotEnvironment(botEnvironment);
		BotEnvironment botEnvTest = new BotEnvironmentImpl();

		botGameConfig.loadPropsToBotEnvironment(botEnvTest);
		
        Assert.assertEquals(botEnvTest.getNextBotConnectionInterval(), 10);
		Assert.assertEquals(botEnvTest.getUpdateInterval(), 200);
		Assert.assertEquals(botEnvTest.getClientAddresses().get(0).getHostString(), 
				"255.255.255.255");
		Assert.assertEquals(botEnvTest.getClientAddresses().get(0).getPort(), 
				1000);
		
		Assert.assertEquals(botEnvTest.getServerAddresses().get(0).getHostString(), 
				"125.124.123.11");
		Assert.assertEquals(botEnvTest.getServerAddresses().get(0).getPort(),
				2000);
		Assert.assertEquals(botEnvTest.getClientAddresses().get(1).getHostString(), 
				"255.255.255.254");
		Assert.assertEquals(botEnvTest.getClientAddresses().get(1).getPort(), 
				1001);
		
		Assert.assertEquals(botEnvTest.getServerAddresses().get(1).getHostString(), 
				"126.124.123.11");
		Assert.assertEquals(botEnvTest.getServerAddresses().get(1).getPort(),
				2001);
		Assert.assertEquals(botEnvTest.isProxy(), true);
		Assert.assertEquals(botEnvTest.isRawData(), true);	
		
	}
	
	@Test()
	public void testBotSettings_load_and_save()
	{
		BotSettings botSettings = new BotSettingsImpl();
		File fn = new File(((BotGameConfigImpl)botGameConfig).DIR_PATH + "/" + "testBotSettings.cfg");
		fn.delete();
		botGameConfig.loadBotSettings(botSettings, "testBotSettings.cfg");
		Assert.assertEquals(botSettings.getAutoConnectInterval(), 10);
		Assert.assertEquals(botSettings.getLogin(), "");
		Assert.assertEquals(botSettings.getName(), "");
		Assert.assertEquals(botSettings.getPassword(), "");
		Assert.assertEquals(botSettings.getServerId(), 0);
		Assert.assertEquals(botSettings.getType(), BotSettings.OUTGAME_TYPE);
		Assert.assertEquals(botSettings.isAutoConnect(), false);
		Assert.assertEquals(botSettings.isDisabled(), false);
		Assert.assertEquals(botSettings.isLogging(), false);
		Assert.assertEquals(botSettings.isModifLogging(), false);
		
		botSettings.setAutoConnectInterval(100);
		botSettings.setLogin("test");
		botSettings.setName("gamebot");
		botSettings.setPassword("testtest");
		botSettings.setServerId(300);
		botSettings.setType(BotSettings.OUTGAME_TYPE);
		botSettings.setAutoConnect(true);
		botSettings.setDisabled(true);
		botSettings.setLogging(true);
		botSettings.setModifLogging(true);
		botGameConfig.saveBotSettings(botSettings, "testBotSettings.cfg", "Test");
		botSettings = new BotSettingsImpl();
		botGameConfig.loadBotSettings(botSettings, "testBotSettings.cfg");

        Assert.assertEquals(botSettings.getAutoConnectInterval(), 100);
		Assert.assertEquals(botSettings.getLogin(), "test");
		Assert.assertEquals(botSettings.getName(), "gamebot");
		Assert.assertEquals(botSettings.getPassword(), "testtest");
		Assert.assertEquals(botSettings.getServerId(), 300);
		Assert.assertEquals(botSettings.getType(), BotSettings.OUTGAME_TYPE);
		Assert.assertEquals(botSettings.isAutoConnect(), true);
		Assert.assertEquals(botSettings.isDisabled(), true);
		Assert.assertEquals(botSettings.isLogging(), true);	
		Assert.assertEquals(botSettings.isModifLogging(), true);
	}
}
