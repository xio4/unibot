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
package com.xiovr.unibot.bot.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.xiovr.unibot.bot.BotAutoconnectRunnable;
import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.BotManager;
import com.xiovr.unibot.bot.BotSettings;

public class BotAutoconnectRunnableImpl implements BotAutoconnectRunnable {

	private List<Integer> curTime;
	private List<BotContext> botContexts;
	private BotManager botManager;
	private Timer timer;
	private TimerTask timerTask;
	private BotEnvironment botEnvironment;

	public BotAutoconnectRunnableImpl(BotManager botManager) {
		this.botManager = botManager;

		this.botEnvironment = botManager.getBotEnvironment();
		curTime = new ArrayList<Integer>(BotManager.BOT_MAX_COUNT);

		for (int i = 0; i < BotManager.BOT_MAX_COUNT; ++i ) {
			curTime.add(0);
		}

		this.botContexts = botManager.getBots(BotSettings.OUTGAME_TYPE);

		this.timerTask = new TimerTask() {
			private int skipTime = 0;

			@Override
			public void run() {
				skipTime++;

				List<BotContext> botContexts = BotAutoconnectRunnableImpl.this.botContexts;
				BotManager botManager = BotAutoconnectRunnableImpl.this.botManager;
				if (skipTime < botEnvironment
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
							skipTime = 0;
							try {
								botManager.connect(botContext.getBotId(),
										BotSettings.OUTGAME_TYPE);
							} catch (Exception e) {
								e.printStackTrace();
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
