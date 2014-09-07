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
	private List<BotContext> ingameBots;
	private List<BotContext> outgameBots;
	private List<BotContext> proxyBots;
	public BotUpdaterImpl(final List<BotContext> ingameBots, final List<BotContext> outgameBots,
			final List<BotContext> proxyBots) {
		this.ingameBots = ingameBots;
		this.outgameBots = outgameBots;
		this.proxyBots = proxyBots;
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
