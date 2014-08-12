package com.xiovr.e5bot.bot.impl;

import java.util.Timer;
import java.util.TimerTask;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotUpdater;
import com.xiovr.e5bot.plugin.ScriptPlugin;

import java.util.List;
public class BotUpdaterImpl implements BotUpdater {

	private Timer timer;
	private TimerTask timerTask;
	private List<BotContext> ingameBots;
	private List<BotContext> outgameBots;
	private List<BotContext> proxyBots;
	public BotUpdaterImpl(final List<BotContext> ingameBots, final List<BotContext> outgameBots,
			final List<BotContext> proxyBots) {
		this.ingameBots = ingameBots;
		this.outgameBots = outgameBots;
		this.proxyBots = proxyBots;
		this.timerTask = new TimerTask() {

			@Override
			public void run() {
//				List<BotContext> botContexts = BotUpdaterImpl.this.botContexts;
				for (BotContext botContext: ingameBots) {
					if (botContext != null && 
							botContext.getStatus() == BotContext.INWORLD_STATUS) {
						ScriptPlugin script = botContext.getScript();
						if (script != null)
							botContext.getScript().update();
					}
				}
				for (BotContext botContext: outgameBots) {
					if (botContext != null && 
							botContext.getStatus() == BotContext.INWORLD_STATUS) {
						ScriptPlugin script = botContext.getScript();
						if (script != null)
							botContext.getScript().update();
					}
				}

				for (BotContext botContext: proxyBots) {
					if (botContext != null && 
							botContext.getStatus() == BotContext.INWORLD_STATUS) {
						ScriptPlugin script = botContext.getScript();
						if (script != null)
							botContext.getScript().update();
					}
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
