package com.xiovr.e5bot.plugin.impl;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotEnvironment;
import com.xiovr.e5bot.bot.BotLogger;
import com.xiovr.e5bot.bot.BotSettings;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.PacketPool;
import com.xiovr.e5bot.bot.packet.RingBufferPool;
import com.xiovr.e5bot.bot.packet.impl.PacketImpl;
import com.xiovr.e5bot.plugin.CryptorCommand;
import com.xiovr.e5bot.plugin.CryptorPlugin;
import com.xiovr.e5bot.plugin.ScriptPlugin;
import com.xiovr.e5bot.plugin.ScriptPluginRunnable;

public class ScriptPluginRunnableImpl implements ScriptPluginRunnable {
	Logger logger = LoggerFactory.getLogger(ScriptPluginRunnableImpl.class);
	private volatile ScriptPlugin script;
	// private volatile BotContext botContext;
	private CryptorPlugin cryptorPlugin;
	private CryptorCommand cryptorCommand;
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
		cryptorCommand = null;
		pck = PacketPool.obtain();
		pck2 = PacketPool.obtain();
		this.botContext = botContext;
		oldTime = System.currentTimeMillis();
		this.bLogging = botContext.getBotSettings().isLogging();
		this.bModifLogging = botContext.getBotSettings().isModifLogging();
		this.botLogger = botContext.getBotLogger();
		this.script = script;
		// this.botContext = botContext;
		this.buf = botContext.getReadBuffer();
		BotEnvironment botEnvironment = botContext.getBotEnvironment();
		this.bRawData = botEnvironment.isRawData();
		this.botType = botContext.getBotSettings().getType();
		this.cryptorPlugin = botContext.getCryptorPlugin();
	}

	@SuppressWarnings("null")
	@Override
	public void run() {
		Thread curThread = Thread.currentThread();
		try {
			while (!curThread.isInterrupted()) {
				pck = buf.poll(pck);
				if (pck == null)
					pck = PacketPool.obtain();
				if (script != null) {
					long time = System.currentTimeMillis();
					if (bRawData)
						script.onPck(pck, time - oldTime);
					if (botType == BotSettings.PROXY_TYPE) {
						if (pck.getType() == Packet.RAW_PCK_FROM_SERVER) {
							cryptorPlugin.decryptFromServer(pck, pck2);
							pck2.setTime(pck.getTime());
							pck2.setType(Packet.PCK_FROM_SERVER);
							if (bLogging)
								botLogger.pckLog(pck2);
							script.onPck(pck2, time - oldTime);
							if (bModifLogging)
								botLogger.pckModifLog(pck2);
							if (pck2.getPosition() > 0) {
								cryptorPlugin.encryptToClient(pck2, pck);
								pck.setType(Packet.RAW_PCK_TO_CLIENT);
								cryptorCommand = cryptorPlugin
										.getNextCommand(cryptorCommand);
								if (cryptorCommand != null) {
									cryptorCommand.execute(pck, pck2);
								} else {
									botContext.getClientConnections()
											.get(pck.getConnStage())
											.writeAndFlush(pck);
								}
							}

						} else if (pck.getType() == Packet.RAW_PCK_FROM_CLIENT) {
							cryptorPlugin.decryptFromClient(pck, pck2);
							pck2.setTime(pck.getTime());
							pck2.setType(Packet.PCK_FROM_CLIENT);
							if (bLogging)
								botLogger.pckLog(pck2);
							script.onPck(pck2, time - oldTime);
							if (bModifLogging)
								botLogger.pckModifLog(pck2);

							if (pck2.getPosition() > 0) {
								cryptorPlugin.encryptToServer(pck2, pck);
								pck.setType(Packet.RAW_PCK_TO_SERVER);
								cryptorCommand = cryptorPlugin
										.getNextCommand(cryptorCommand);
								if (cryptorCommand != null) {
									cryptorCommand.execute(pck, pck2);
								} else {
									botContext.getServerConnections()
											.get(pck.getConnStage())
											.writeAndFlush(pck);
								}
							}
						}
						if (bRawData)
							script.onPck(pck, time - oldTime);
					} else if (botType == BotSettings.OUTGAME_TYPE) {
						cryptorPlugin.decryptFromServer(pck, pck2);
						pck2.setTime(pck.getTime());
						pck2.setType(Packet.PCK_FROM_SERVER);
						if (bLogging)
							botLogger.pckLog(pck2);
						script.onPck(pck2, time - oldTime);
						if (bModifLogging)
							botLogger.pckModifLog(pck2);

						cryptorCommand = cryptorPlugin
								.getNextCommand(cryptorCommand);
						if (pck2.getPosition() > 0 && cryptorCommand != null)
							cryptorCommand.execute(pck, pck2);

					} else if (botType == BotSettings.INGAME_TYPE) {
						if (bLogging)
							botLogger.pckLog(pck);
						script.onPck(pck, time - oldTime);
						if (bModifLogging)
							botLogger.pckModifLog(pck);

						cryptorCommand = cryptorPlugin
								.getNextCommand(cryptorCommand);
						if (pck2.getPosition() > 0 && cryptorCommand != null)
							cryptorCommand.execute(pck, pck);
					}
					oldTime = time;
				}
			}
		} catch (InterruptedException ie) {
			curThread.interrupt();
		} catch (Exception e) {
			// TODO Need set web-logger
			logger.error("Script " + script.getName() + " exception");
			e.printStackTrace();
		}
	}

	@Override
	public void setScript(@NonNull ScriptPlugin script) {
		this.script = script;
	}

}
