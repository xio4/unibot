package com.xiovr.unibot.bot;

import org.eclipse.jdt.annotation.NonNull;

public interface BotMessageTransferRunnable extends Runnable {
	public static final int MAX_CAPACITY = 100;
	public void init(@NonNull BotManager botManager);
	public void sendMsg(@NonNull String sender, 
			@NonNull String receiver, @NonNull String msg);
}
