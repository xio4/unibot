package com.xiovr.e5bot.bot;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;
public interface BotAutoconnectRunnable {
//	CopyOnWriteArrayList<BotContext> botContexts;
	public void start();
	public void stop();

}
