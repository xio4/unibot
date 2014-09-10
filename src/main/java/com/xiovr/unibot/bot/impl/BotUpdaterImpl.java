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

import java.util.Timer;
import java.util.TimerTask;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotUpdater;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.ScriptPlugin;

import java.util.List;
public class BotUpdaterImpl implements BotUpdater {

	private Timer timer;
	private TimerTask timerTask;
//	private List<BotContext> ingameBots;
//	private List<BotContext> outgameBots;
//	private List<BotContext> proxyBots;
	public BotUpdaterImpl(final List<BotContext> ingameBots, final List<BotContext> outgameBots,
			final List<BotContext> proxyBots) {
//		this.ingameBots = ingameBots;
//		this.outgameBots = outgameBots;
//		this.proxyBots = proxyBots;
		this.timerTask = new TimerTask() {
			private void updatePlugins(BotContext botContext) {
				
					if (botContext != null) {
						CryptorPlugin crypt = botContext.getCryptorPlugin();
						if (crypt != null)
							crypt.update();
					}
					if (botContext != null && 
							botContext.getStatus() == BotContext.INWORLD_STATUS) {
						ScriptPlugin script = botContext.getScript();
						if (script != null)
							script.update();
					}
			}

			@Override
			public void run() {
//				List<BotContext> botContexts = BotUpdaterImpl.this.botContexts;
				for (BotContext botContext: ingameBots) {
					updatePlugins(botContext);
				}
				for (BotContext botContext: outgameBots) {
					updatePlugins(botContext);
				}
				for (BotContext botContext: proxyBots) {
					updatePlugins(botContext);
				}
			}
		};
		timer = new Timer();
	}

	@Override
	public void startUpdating(long interval) {
		timer.scheduleAtFixedRate(timerTask, interval, interval);
	}

	@Override
	public void stopUpdating() {
		timer.cancel();

	}

}
