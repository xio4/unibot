package com.xiovr.e5bot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.e5bot.TestBase;
import com.xiovr.e5bot.utils.exceptions.BotDoNotExistsException;

/**
 * @author xio4
 * TDD
 */
public class BotManagerTest extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(BotManagerTest.class);
	@Autowired 
	BotManager botManager;

	@Test()
	public void testBotManager_create_count_destroy_bot()
	{
		botManager.clear();
		Bot bot = botManager.createBot();
		Assert.assertNotNull(bot);
		Assert.assertEquals(botManager.botsCount(), 1);

		bot = botManager.createBot();
		Assert.assertEquals(botManager.botsCount(), 2);
		bot = botManager.getBot(bot.getBotId());
		Assert.assertNotNull(bot);
		botManager.destroyBot(bot.getBotId());
		bot = botManager.createBot();
		Assert.assertEquals(botManager.botsCount(), 1);

	}
	@Test(expectedExceptions = {BotDoNotExistsException.class})
	public void testBotManager_destroy_do_not_exists_bot_exception()
	{
		botManager.destroyBot(1000000);
	}
	@Test(dependsOnMethods={"testBotManager_create_count_destroy_bot"})
	public void testBotManager_start_stop_bot()
	{

		System.out.println("LKJLJKLJLKJLKJLKJLKJLKJ");
		Bot bot = botManager.createBot();
		botManager.start(bot.getBotId());
		Assert.assertNotEquals(bot.getStatus(), Bot.OFFLINE_STATUS);
		botManager.stop(bot.getBotId());
	}
}
