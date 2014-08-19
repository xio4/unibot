package com.xiovr.e5bot.bot.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.xiovr.e5bot.bot.BotAutoconnectRunnable;
import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotManager;
import com.xiovr.e5bot.bot.BotSettings;
import com.xiovr.e5bot.plugin.ScriptPlugin;

public class BotAutoconnectRunnableImpl implements BotAutoconnectRunnable {

	private List<Integer> curTime;
	private List<BotContext> botContexts;
	private BotManager botManager;
	private Timer timer;
	private TimerTask timerTask;

	public BotAutoconnectRunnableImpl(BotManager botManager) {
		this.botManager = botManager;
		curTime = new ArrayList<Integer>(BotManager.BOT_MAX_COUNT);
		for (int i = 0; i < curTime.size(); ++i) {
			curTime.set(i, 0);
		}
		this.botContexts = botManager.getBots(BotSettings.OUTGAME_TYPE);
		this.timerTask = new TimerTask() {
			private int skipTime = 0;

			@Override
			public void run() {
				skipTime++;

				List<BotContext> botContexts = BotAutoconnectRunnableImpl.this.botContexts;
				BotManager botManager = BotAutoconnectRunnableImpl.this.botManager;
				if (skipTime < botManager.getBotEnvironment()
						.getNextBotConnectionInterval())
					return;
				for (int i = 0; i < botContexts.size(); ++i) {
					BotContext botContext = botContexts.get(i);
					if (botContext != null
							&& botContext.getBotSettings().getAutoConnect()
							&& botContext.getStatus() == BotContext.OFFLINE_STATUS) {
						Integer tm = curTime.get(i);
						tm++;
						if (tm < botContext.getBotSettings()
								.getAutoConnectInterval())
							curTime.set(i, tm);
						else {
							curTime.set(i, 0);
							try {
								botManager.connect(botContext.getBotId(),
										BotSettings.OUTGAME_TYPE);
							} catch (Exception e) {

							}
						}

					}
				}
			}

		};
		timer = new Timer();
	}

	@Override
	public void start() {
		timer.scheduleAtFixedRate(timerTask, 1000, 1000);

	}

	@Override
	public void stop() {
		timer.cancel();

	}

}
