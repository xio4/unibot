package com.xiovr.e5bot.bot;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.testng.annotations.Test;

import com.xiovr.e5bot.TestBase;
import com.xiovr.e5bot.bot.impl.BotAutoconnectRunnableImpl;
import com.xiovr.e5bot.bot.impl.BotContextImpl;
import com.xiovr.e5bot.utils.exceptions.BotDoNotExistsException;

public class BotAutoconnectTest extends TestBase {
	@Test
	public void botAutoconnect_start_run_and_stop()
	{
		BotSettings bs = EasyMock.createMock(BotSettings.class);
		EasyMock.expect(bs.getAutoConnect()).andReturn(true).anyTimes();
		EasyMock.expect(bs.getAutoConnectInterval()).andReturn(1).anyTimes();
		
		BotEnvironment be = EasyMock.createMock(BotEnvironment.class);
		EasyMock.expect(be.getNextBotConnectionInterval()).andReturn(1).anyTimes();
		
		final List<BotContext> bots = new ArrayList<BotContext>();
		for (int i=0; i < 2; ++i) {
			BotContext bc = new BotContextImpl();
			bc.setBotEnvironment(be);
			bc.setBotSettings(bs);
			bc.setStatus(BotContext.OFFLINE_STATUS);
			bc.setBotId(i);
			bots.add(bc);
		}

		// Create BotManager mock
		BotManager bm = EasyMock.createMock(BotManager.class);
		EasyMock.expect(bm.getBots(EasyMock.anyInt())).andReturn(bots);
		EasyMock.expect(bm.getBotEnvironment()).andReturn(be);
		try {
			bm.connect(EasyMock.anyInt(), EasyMock.anyInt());
			EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
				@Override
				public Object answer() throws Throwable {
					bots.get((Integer)EasyMock.getCurrentArguments()[0])
						.setStatus(BotContext.CONN_STATUS);
					return null;
				}
			}).times(2);
		} catch (BotDoNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EasyMock.replay(bs, be, bm);

		BotAutoconnectRunnable bar = new BotAutoconnectRunnableImpl(bm);
		bar.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bar.stop();
		
		EasyMock.verify(bs, be, bm);
	}

}
