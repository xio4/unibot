package com.xiovr.unibot.bot;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotManager;
import com.xiovr.unibot.bot.BotMessageTransferRunnable;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.bot.impl.BotContextImpl;
import com.xiovr.unibot.bot.impl.BotMessageTransferRunnableImpl;
import com.xiovr.unibot.bot.impl.BotSettingsImpl;
import com.xiovr.unibot.plugin.ScriptPlugin;

public class BotMessageTransferTest extends TestBase {
	private BotMessageTransferRunnable botMessageTransfer;
	private Thread botMessageTransferThread;
	private BotManager botManagerMock;

	private final String[][] data = new String[][] {
			{ "one", "two", "hello1" }, { "anonymous", "three", "no msg" },
			{ "zero", "five", "hello2" }, { "anonymous", "one", "no msg" },
			{ "zero", "four", "hello3" }, { "anonymous", "zero", "no msg" }

	};
	private List<ScriptPlugin> scriptsMock;

	private BotContext createFakeBot(final String sender,
			final String receiver, final String msg) {

		BotContext bot = new BotContextImpl();
		BotSettings botSettings = new BotSettingsImpl();
		botSettings.setName(receiver);
		bot.setBotSettings(botSettings);
		ScriptPlugin script = EasyMock.createMock(ScriptPlugin.class);
		script.onReadBotMsg(EasyMock.anyObject(String.class),
				EasyMock.anyObject(String.class));

		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			@Override
			public Object answer() throws Throwable {

				// Check sender
				Assert.assertTrue(sender.equals(EasyMock.getCurrentArguments()[0]));
				// Check message
				Assert.assertTrue(msg.equals(EasyMock.getCurrentArguments()[1]));

				return null;
			}
		}).anyTimes();

		bot.setScript(script);
		scriptsMock.add(script);
		EasyMock.replay(script);

		return bot;
	}

	@SuppressWarnings("null")
	@BeforeClass
	public void before() {
		botMessageTransfer = new BotMessageTransferRunnableImpl();
		botMessageTransferThread = new Thread(botMessageTransfer);
		botManagerMock = EasyMock.createMock(BotManager.class);
		scriptsMock = new ArrayList<ScriptPlugin>();
		// Create fake outgame bots
		List<BotContext> outgameBots = new ArrayList<BotContext>();
		outgameBots.add(createFakeBot(data[0][0], data[0][1], data[0][2]));
		outgameBots.add(createFakeBot(data[1][0], data[1][1], data[1][2]));

		EasyMock.expect(botManagerMock.getBots(BotSettings.OUTGAME_TYPE))
				.andReturn(outgameBots).anyTimes();

		// Create fake ingame bots
		List<BotContext> ingameBots = new ArrayList<BotContext>();
		ingameBots.add(createFakeBot(data[2][0], data[2][1], data[2][2]));
		ingameBots.add(createFakeBot(data[3][0], data[3][1], data[3][2]));

		EasyMock.expect(botManagerMock.getBots(BotSettings.INGAME_TYPE))
				.andReturn(outgameBots).anyTimes();

		// Create fake proxy bots
		List<BotContext> proxyBots = new ArrayList<BotContext>();
		proxyBots.add(createFakeBot(data[4][0], data[4][1], data[4][2]));
		proxyBots.add(createFakeBot(data[5][0], data[5][1], data[5][2]));

		EasyMock.expect(botManagerMock.getBots(BotSettings.PROXY_TYPE))
				.andReturn(proxyBots).anyTimes();

		EasyMock.replay(botManagerMock);
		
		botMessageTransfer.init(botManagerMock);

	}

	@SuppressWarnings("null")
	@Test
	public void botMessageTransfer_create_run_and_check_handle_msg() {
		botMessageTransferThread.start();
		try {
			Thread.sleep(100);
			for (int i = 0; i < data.length; ++i) {
				botMessageTransfer.sendMsg(data[i][0], data[i][1], data[i][2]);
			}

			Thread.sleep(100);
		} catch (InterruptedException e) {
//			e.printStackTrace();
		}

		EasyMock.verify(botManagerMock);
		for (ScriptPlugin script: scriptsMock) {
			EasyMock.verify(script);
		}
		
		botMessageTransferThread.interrupt();
	}

}
