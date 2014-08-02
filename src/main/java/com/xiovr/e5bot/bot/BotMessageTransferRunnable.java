package com.xiovr.e5bot.bot;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public interface BotMessageTransferRunnable extends Runnable {
	public void setBotContextList(@NonNull List<BotContext> bots);
	public void sendMsg(@NonNull String sender, 
			@NonNull String receiver, @NonNull String msg);
}
