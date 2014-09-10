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

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotManager;
import com.xiovr.unibot.bot.BotMessageTransferRunnable;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.plugin.ScriptPlugin;

public class BotMessageTransferRunnableImpl implements
		BotMessageTransferRunnable {
	@SuppressWarnings("unused")
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

		try {
			while (!curThread.isInterrupted()) {
				MessageWrapper msgW;
				msgW = queue.take();

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
