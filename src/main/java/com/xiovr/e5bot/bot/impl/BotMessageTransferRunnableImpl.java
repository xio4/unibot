package com.xiovr.e5bot.bot.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotManager;
import com.xiovr.e5bot.bot.BotMessageTransferRunnable;
import com.xiovr.e5bot.bot.BotSettings;
import com.xiovr.e5bot.plugin.ScriptPlugin;

public class BotMessageTransferRunnableImpl implements
		BotMessageTransferRunnable {
	private Logger logger = LoggerFactory
			.getLogger(BotMessageTransferRunnableImpl.class);
	private BotManager botManager;
	private BlockingQueue<MessageWrapper> queue;

	public BotMessageTransferRunnableImpl() {
		queue = new LinkedBlockingQueue<MessageWrapper>(MAX_CAPACITY);
	}

	class MessageWrapper {
		public String sender;
		public String receiver;
		public String msg;
	}

	private boolean findAndRunMsgHandler(List<BotContext> bots,
			MessageWrapper msgW) {
		for (BotContext bot : bots) {
			if (bot.getBotSettings().getName().equals(msgW.receiver)) {
				ScriptPlugin script = bot.getScript();
				if (script != null) {
					script.onReadBotMsg(msgW.sender, msgW.msg);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		Thread curThread = Thread.currentThread();
		System.out.println("\n\n\nThread is started\n\n\n\n\n");

		try {
			while (!curThread.isInterrupted()) {
				MessageWrapper msgW;
				msgW = queue.take();

				System.out.println("\n\n\n Message: " + msgW.sender + " "
						+ msgW.receiver + " " + msgW.msg);

				// Find in ingame bots
				List<BotContext> bots = botManager
						.getBots(BotSettings.INGAME_TYPE);
				if (findAndRunMsgHandler(bots, msgW)) {
					continue;
				}

				// Find in outgame bots
				bots = botManager.getBots(BotSettings.OUTGAME_TYPE);
				if (findAndRunMsgHandler(bots, msgW)) {
					continue;
				}

				// Find in proxy bots
				bots = botManager.getBots(BotSettings.PROXY_TYPE);
				if (findAndRunMsgHandler(bots, msgW)) {
					continue;
				}
			}
		} catch (InterruptedException e) {
			// Restore interrupt flag
			curThread.interrupt();
			e.printStackTrace();
		}

	}

	@Override
	public void init(@NonNull BotManager botManager) {
		this.botManager = botManager;
	}

	@Override
	public void sendMsg(@NonNull String sender, @NonNull String receiver,
			@NonNull String msg) {

		MessageWrapper mw = new MessageWrapper();
		mw.msg = msg;
		mw.receiver = receiver;
		mw.sender = sender;
		try {
			queue.put(mw);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
