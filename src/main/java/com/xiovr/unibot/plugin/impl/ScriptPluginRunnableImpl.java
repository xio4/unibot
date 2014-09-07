package com.xiovr.unibot.plugin.impl;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.BotLogger;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.PacketPool;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.bot.packet.impl.PacketImpl;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.ScriptPlugin;
import com.xiovr.unibot.plugin.ScriptPluginRunnable;

public class ScriptPluginRunnableImpl implements ScriptPluginRunnable {
	Logger logger = LoggerFactory.getLogger(ScriptPluginRunnableImpl.class);
	private volatile ScriptPlugin script;
	// private volatile BotContext botContext;
	private CryptorPlugin cryptorPlugin;
	private RingBufferPool<Packet> buf;
	private Packet pck;
	private Packet pck2;
	private long oldTime;
	private boolean bRawData;
	private int botType;
	private boolean bLogging;
	private boolean bModifLogging;
	private BotLogger botLogger;
	private BotContext botContext;

	public ScriptPluginRunnableImpl(BotContext botContext) {
		bRawData = false;
		// pck = PacketPool.obtain();
		pck2 = PacketPool.obtain();
		pck2.clear();
		this.botContext = botContext;
		oldTime = System.currentTimeMillis();
		this.bLogging = botContext.getBotSettings().getLogging();
		this.bModifLogging = botContext.getBotSettings().getModifLogging();
		this.botLogger = botContext.getBotLogger();
		this.script = script;
		// this.botContext = botContext;
		this.buf = botContext.getReadBuffer();
		BotEnvironment botEnvironment = botContext.getBotEnvironment();
		this.bRawData = botEnvironment.getRawData();
		this.botType = botContext.getBotSettings().getType();
		this.cryptorPlugin = botContext.getCryptorPlugin();
	}

	@SuppressWarnings("null")
	@Override
	public void run() {
		Thread curThread = Thread.currentThread();
		try {
			while (!curThread.isInterrupted()) {

				// pck = buf.poll(pck);
				pck = buf.poll();
//				System.out
//						.println("Packet to script plugin has been read pck.len="
//								+ pck.getPosition());
				if (pck == null)
					continue;
				// if (script != null) {
				long time = System.currentTimeMillis();
				if (bRawData && script != null)
					script.onPck(pck, time - oldTime);
				if (botType == BotSettings.PROXY_TYPE) {
					if (pck.getType() == Packet.RAW_PCK_FROM_SERVER) {

						cryptorPlugin.decryptFromServer(pck, pck2);
						pck2.setConnStage(pck.getConnStage());
						pck2.setTime(pck.getTime());
						pck2.setType(Packet.PCK_FROM_SERVER);

						if (bLogging)
							botLogger.pckLog(pck2);
						if (script != null)
							script.onPck(pck2, time - oldTime);
						if (bModifLogging)
							botLogger.pckModifLog(pck2);
						cryptorPlugin.modifyServerPacket(pck2);
						if (pck2.getPosition() > 2) {
							cryptorPlugin.encryptToClient(pck2, pck);
							pck.setType(Packet.RAW_PCK_TO_CLIENT);
							if (bRawData && script != null)
								script.onPck(pck, time - oldTime);
							cryptorPlugin.execServerCommand(pck, pck2);
						}

					} else if (pck.getType() == Packet.RAW_PCK_FROM_CLIENT) {
						cryptorPlugin.decryptFromClient(pck, pck2);
						pck2.setConnStage(pck.getConnStage());
						pck2.setTime(pck.getTime());
						pck2.setType(Packet.PCK_FROM_CLIENT);

//						System.out.println("Packet from client decrypted size=" + pck2.getPosition());
						if (bLogging)
							botLogger.pckLog(pck2);
						if (script != null)
							script.onPck(pck2, time - oldTime);
						if (bModifLogging)
							botLogger.pckModifLog(pck2);

						cryptorPlugin.modifyClientPacket(pck2);
						if (pck2.getPosition() > 2) {
							cryptorPlugin.encryptToServer(pck2, pck);
							pck.setType(Packet.RAW_PCK_TO_SERVER);
							if (bRawData && script != null)
								script.onPck(pck, time - oldTime);
								cryptorPlugin.execClientCommand(pck, pck2);
						}
					}

				} else if (botType == BotSettings.OUTGAME_TYPE) {
					cryptorPlugin.decryptFromServer(pck, pck2);
					pck2.setTime(pck.getTime());
					pck2.setType(Packet.PCK_FROM_SERVER);
					if (bLogging)
						botLogger.pckLog(pck2);
					if (script != null)
						script.onPck(pck2, time - oldTime);
					if (bModifLogging)
						botLogger.pckModifLog(pck2);

					if (pck2.getPosition() > 2) {
						cryptorPlugin.execServerCommand(pck2, pck2);
					} else {
						PacketPool.free(pck);
					}

				} else if (botType == BotSettings.INGAME_TYPE) {
					if (bLogging)
						botLogger.pckLog(pck);
					if (script != null)
						script.onPck(pck, time - oldTime);
					if (bModifLogging)
						botLogger.pckModifLog(pck);

					if (pck2.getPosition() > 2 ) {
					} else {

					}
				}
				oldTime = time;
				// }

			}
		} catch (InterruptedException ie) {
			curThread.interrupt();
		} catch (Exception e) {
			// TODO Need set web-logger
			e.printStackTrace();
			if (script != null)
				logger.error("Script " + script.getName() + " exception");
		}
	}

	@Override
	public void setScript(@NonNull ScriptPlugin script) {
		this.script = script;
	}

}
