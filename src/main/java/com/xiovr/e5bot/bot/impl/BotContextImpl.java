package com.xiovr.e5bot.bot.impl;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotEnvironment;
import com.xiovr.e5bot.bot.BotLogger;
import com.xiovr.e5bot.bot.BotSettings;
import com.xiovr.e5bot.bot.BotThreadFacade;
import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.PacketPool;
import com.xiovr.e5bot.bot.packet.RingBufferPool;
import com.xiovr.e5bot.bot.packet.impl.PacketImpl;
import com.xiovr.e5bot.plugin.CryptorCommand;
import com.xiovr.e5bot.plugin.CryptorPlugin;
import com.xiovr.e5bot.plugin.ScriptPlugin;

public class BotContextImpl implements BotContext {

	Logger logger = LoggerFactory.getLogger(BotContextImpl.class);
	private int botId;
	private int status;
	private BotEnvironment botEnvironment;
	private CryptorPlugin cryptorPlugin;
	private Packet bcPck;
	private RingBufferPool<Packet> readBuf;
	private RingBufferPool<Packet> writeServerBuf;
	private RingBufferPool<Packet> writeClientBuf;
	private BotConnection serverConnection;
	private BotConnection clientConnection;
	private BotSettings botSettings;
	private ScriptPlugin script;
	private BotThreadFacade botThreadFacade;
	private CryptorCommand cryptorCommand;
	private BotLogger botLogger;

	public BotContextImpl() {
		super();
		botId = -1;
	}

	@Override
	public int getBotId() {
		return botId;
	}

	@Override
	public void setBotId(int botId) {
		this.botId = botId;
	}

	@Override
	public BotEnvironment getBotEnvironment() {
		return botEnvironment;
	}

	@Override
	public void setBotEnvironment(@NonNull BotEnvironment botEnvironment) {
		this.botEnvironment = botEnvironment;
	}

	@Override
	public BotConnection getServerConnection() {
		return this.serverConnection;
	}

	@Override
	public void setServerConnection(BotConnection botConnection) {
		this.serverConnection = botConnection;
	}

	@Override
	public BotConnection getClientConnection() {
		return clientConnection;
	}

	@Override
	public void setClientConnection(BotConnection botConnection) {
		this.clientConnection = botConnection;
	}

	// TODO reconnect not implemented yet
	@Override
	public void reconnect(int seconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int getStatus() {
		return this.status;
	}

	@SuppressWarnings("null")
	@Override
	public void sendToServer(Packet pck) throws InterruptedException {
		if (pck == null)
			return;
        long time = System.currentTimeMillis();
        pck.setType(Packet.PCK_TO_SERVER);
        if (botSettings.isLogging())
        	botLogger.pckLog(pck);
        bcPck = PacketPool.obtain(); 
        cryptorPlugin.encryptToServer(pck, bcPck);
        bcPck.setType(Packet.RAW_PCK_TO_SERVER);
        bcPck.setTime(time);
        PacketPool.free(pck);
        final Packet chPck = writeServerBuf.put(bcPck);
        PacketPool.free(chPck);
        	
	}

	@SuppressWarnings("null")
	@Override
	public void sendToClient(Packet pck) throws InterruptedException {
		if (pck == null)
			return;
        long time = System.currentTimeMillis();
        pck.setType(Packet.PCK_TO_CLIENT);
        if (botSettings.isLogging())
        	botLogger.pckLog(pck);
        bcPck = PacketPool.obtain(); 
        cryptorPlugin.encryptToClient(pck, bcPck);
        bcPck.setType(Packet.RAW_PCK_TO_CLIENT);
        bcPck.setTime(time);
        PacketPool.free(pck);
        final Packet chPck = writeServerBuf.put(bcPck);
        PacketPool.free(chPck);
	}

	//TODO sendMsgToBot not implemented yet
	@Override
	public boolean sendMsgToBot(String name, String msg) {
		logger.error("sendMsgToBot not implemented yet");
		return false;
	}

	@Override
	public void setScript(@NonNull ScriptPlugin script) {
		this.script = script;
	}

	@Override
	public ScriptPlugin getScript() {
		return this.script;
	}

	@Override
	public void setCryptorPlugin(@NonNull CryptorPlugin cryptor) {
		this.cryptorPlugin = cryptor;
	}

	@Override
	public CryptorPlugin getCryptorPlugin() {
		return this.cryptorPlugin;
	}

	@Override
	public RingBufferPool<Packet> getWriteClientBuffer() {
		return this.writeClientBuf;
	}

	@Override
	public void setWriteClientBuffer(@NonNull RingBufferPool<Packet> sendBuf) {
		this.writeClientBuf = sendBuf;

	}

	@Override
	public RingBufferPool<Packet> getWriteServerBuffer() {
		return this.writeServerBuf;
	}

	@Override
	public void setWriteServerBuffer(@NonNull RingBufferPool<Packet> sendBuf) {
		this.writeServerBuf = sendBuf;

	}

	@Override
	public RingBufferPool<Packet> getReadBuffer() {
		return this.readBuf;
	}

	@Override
	public void setReadBuffer(@NonNull RingBufferPool<Packet> readBuf) {
		this.readBuf = readBuf;

	}

	@Override
	public void setBotThreadFacade(@NonNull BotThreadFacade botThread) {
		this.botThreadFacade = botThread;

	}

	@Override
	public BotThreadFacade getBotThreadFacade() {
		return botThreadFacade;
	}

	@Override
	public void setBotSettings(@NonNull BotSettings botSettings) {
		this.botSettings = botSettings;

	}

	@Override
	public BotSettings getBotSettings() {
		return this.botSettings;
	}

	//TODO log not implemented yet
	@Override
	public void log(int type, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public CryptorCommand getCryptorCommand() {
		return cryptorCommand;
	}

	@Override
	public void setCryptorCommand(
			@NonNull CryptorCommand cryptorCommand) {
		this.cryptorCommand = cryptorCommand;
	}

	@Override
	public void setBotLogger(BotLogger botLogger) {
		this.botLogger = botLogger;
		
	}

	@Override
	public BotLogger getBotLogger() {
		return botLogger;
	}

}
