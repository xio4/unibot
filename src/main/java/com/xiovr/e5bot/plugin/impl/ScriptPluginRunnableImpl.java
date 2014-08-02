package com.xiovr.e5bot.plugin.impl;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotEnvironment;
import com.xiovr.e5bot.bot.BotLogger;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.RingBufferPool;
import com.xiovr.e5bot.bot.packet.impl.PacketImpl;
import com.xiovr.e5bot.plugin.CryptorCommand;
import com.xiovr.e5bot.plugin.CryptorPlugin;
import com.xiovr.e5bot.plugin.ScriptPlugin;
import com.xiovr.e5bot.plugin.ScriptPluginRunnable;

public class ScriptPluginRunnableImpl implements ScriptPluginRunnable {
	Logger logger = LoggerFactory.getLogger("ScriptPluginThreadImpl");
	private volatile ScriptPlugin script;
//	private volatile BotContext botContext;
	private CryptorPlugin cryptorPlugin;
	private CryptorCommand cryptorCommand;
	private RingBufferPool<Packet> buf;
	private @NonNull Packet pck;
	private @NonNull Packet pck2;
	private long oldTime;
	private boolean bRawData;
	private boolean bClientProxy;
	private boolean bLogging; 
	private BotLogger botLogger;

	public ScriptPluginRunnableImpl(BotContext botContext) {
		bRawData = false;
		bClientProxy = false;
		cryptorCommand = null;
		pck = new PacketImpl();
		pck2 = new PacketImpl();
		oldTime = System.currentTimeMillis();
		this.bLogging = botContext.getBotSettings().isLogging();
		this.botLogger = botContext.getBotLogger();
		this.script = script;
//		this.botContext = botContext;
		this.buf = botContext.getReadBuffer();
		BotEnvironment botEnvironment = botContext.getBotEnvironment();
		this.bRawData = botEnvironment.isRawData();
		this.bClientProxy = botContext.getSettings().isClientProxy();
		this.cryptorPlugin = botContext.getCryptorPlugin();
	}

	@SuppressWarnings("null")
	@Override
	public void run() {
		Thread curThread = Thread.currentThread();
		try {
			while (!curThread.isInterrupted()) {
				pck = buf.poll(pck);
				if (script != null) {
					long time = System.currentTimeMillis();
					if (bRawData)
						script.onPck(pck, time - oldTime);
					if (bClientProxy) {
						if (pck.getType() == Packet.RAW_PCK_FROM_SERVER) {
							cryptorPlugin.decryptFromServer(pck, pck2);
							pck2.setTime(pck.getTime());
							pck2.setType(Packet.PCK_FROM_SERVER);
							if (bLogging)
								botLogger.pckLog(pck2);
							script.onPck(pck2, time - oldTime);
							cryptorPlugin.encryptToClient(pck2, pck);
							pck.setType(Packet.RAW_PCK_TO_CLIENT);
						} else if (pck.getType() == Packet.RAW_PCK_FROM_CLIENT) {
							cryptorPlugin.decryptFromClient(pck, pck2);
							pck2.setTime(pck.getTime());
							pck2.setType(Packet.PCK_FROM_CLIENT);
							if (bLogging)
								botLogger.pckLog(pck2);
							script.onPck(pck2, time - oldTime);
							cryptorPlugin.encryptToServer(pck2, pck);
							pck.setType(Packet.RAW_PCK_TO_SERVER);
						}
						if (bRawData)
							script.onPck(pck, time - oldTime);
					} else {
						cryptorPlugin.decryptFromServer(pck, pck2);
						pck2.setTime(pck.getTime());
						pck2.setType(Packet.PCK_FROM_SERVER);
							if (bLogging)
								botLogger.pckLog(pck2);
						script.onPck(pck2, time - oldTime);

					}
					cryptorCommand = cryptorPlugin
							.getNextCommand(cryptorCommand);
					if (pck2.getBuf().limit() > 0)
						cryptorCommand.execute(pck2);
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
	public void notifyUpdate() {
		try {
			script.update();
		} catch (Exception e) {
			// TODO It need sets service locator web-logger
			logger.error("Error update script=" + script.getName());
			e.printStackTrace();
		}

	}

	@Override
	public void setScript(@NonNull ScriptPlugin script) {
		this.script = script;
	}

}
