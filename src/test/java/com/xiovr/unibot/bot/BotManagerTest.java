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

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.BotGameConfig;
import com.xiovr.unibot.bot.BotManager;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.bot.impl.BotEnvironmentImpl;
import com.xiovr.unibot.bot.impl.BotGameConfigImpl;
import com.xiovr.unibot.bot.network.ConnectionFactory;
import com.xiovr.unibot.plugin.PluginLoader;
import com.xiovr.unibot.plugin.impl.PluginLoaderImpl;
import com.xiovr.unibot.utils.exceptions.BotDoNotExistsException;

/**
 * @author xio4
 * TDD
 */
public class BotManagerTest extends TestBase {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BotManagerTest.class);
	@Autowired 
	BotManager botManager;
	
	private PluginLoader pluginLoader;
	private BotGameConfig botGameConfig;
	
	@DataProvider(name="getTypes")
	public static Object[][] getTypes() {
		return new Object[][] {
				{null, BotSettings.INGAME_TYPE},
				{null, BotSettings.PROXY_TYPE},
				{null, BotSettings.OUTGAME_TYPE}
		};
	}
	
	@BeforeClass
	public void before()
	{
		// Load fake cryptor plugin
		this.pluginLoader = new PluginLoaderImpl();
		String dir = getClass().getProtectionDomain().getCodeSource()
				.getLocation().toString().substring(6);
		try {
			this.pluginLoader.loadCryptorPlugin("/" + dir + "cryptor_fake.jar");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create fake game config
		botGameConfig = new BotGameConfigImpl();
		
	}
	
	@AfterClass
	public void after()
	{

	}

	@SuppressWarnings("deprecation")
	private @NonNull BotEnvironment createBotEnvironment() {
		BotEnvironment botEnv = new BotEnvironmentImpl();
		botEnv.setPortRangeMin(10000);
		botEnv.setPortRangeMax(11000);
		botEnv.setProxy(true);
		botEnv.setRawData(true);
		botEnv.setUpdateInterval(100);
		botEnv.setNextBotConnectionInterval(10);
		InetSocketAddress isaS = new InetSocketAddress("localhost", 8889);
		List<InetSocketAddress> lisaS = new ArrayList<InetSocketAddress>();
		lisaS.add(isaS);
		botEnv.setServerAddresses(lisaS);
		InetSocketAddress isaC = new InetSocketAddress("localhost", 8000);
		List<InetSocketAddress> lisaC = new ArrayList<InetSocketAddress>();
		lisaC.add(isaC);
		botEnv.setClientAddresses(lisaC);
		return botEnv;

	}
	
	@SuppressWarnings("null")
	@Test(dataProvider="getTypes")
	public void testBotManager_create_count_destroy_bot(Object reserved, int botType)
	{
		try {
		// Mocking connection factory
		ConnectionFactory connFactory = EasyMock.createMock(ConnectionFactory.class);
		if (botType == BotSettings.PROXY_TYPE) {
			connFactory.createBotConnectionClient(EasyMock.anyObject(BotContext.class), EasyMock.anyInt());
			EasyMock.expectLastCall();
		}
		connFactory.createBotConnectionServer(EasyMock.anyObject(BotContext.class), EasyMock.anyInt());
		EasyMock.expectLastCall();
		EasyMock.replay(connFactory);
		
		
		botManager.clear();
		botManager.setBotEnvironment(createBotEnvironment());
		botManager.setConnectionFactory(connFactory);
		botManager.setPluginLoader(pluginLoader);
		botManager.setBotGameConfig(botGameConfig);
		
		BotContext bot = botManager.createBot(botType, "bot_test.cfg");

		Assert.assertNotNull(bot);
		Assert.assertEquals(botManager.botsCount(botType), 1);
		Assert.assertEquals(bot.getBotId(), 0);
		Assert.assertNotNull(bot.getBotEnvironment());
		Assert.assertNotNull(bot.getBotSettings());
		Assert.assertNotNull(bot.getCryptorPlugin());
		Assert.assertNotNull(bot.getReadBuffer());
		Assert.assertNotNull(bot.getScriptPluginFacade());
		Assert.assertNotNull(bot.getScriptPluginFacade().getScriptPluginRunnable());
		Assert.assertNotNull(bot.getScriptPluginFacade().getScriptPluginThread());
		Assert.assertEquals(bot.getStatus(), BotContext.OFFLINE_STATUS);
		Assert.assertEquals(bot.getConnectStage(), 0);
		// Mock overloaded
		Assert.assertNotNull(bot.getClientConnections());
		// Mock overloaded
		Assert.assertNotNull(bot.getServerConnections());


		EasyMock.verify(connFactory);

		try {
			botManager.destroyBot(0, botType);
		} catch (BotDoNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(botManager.botsCount(botType), 0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expectedExceptions = {BotDoNotExistsException.class})
	public void testBotManager_destroy_do_not_exists_bot_exception() throws BotDoNotExistsException
	{
		this.botManager.destroyBot(10000000, BotSettings.PROXY_TYPE);
	}
}
