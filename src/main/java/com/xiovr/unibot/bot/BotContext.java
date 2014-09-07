package com.xiovr.unibot.bot;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.network.BotConnection;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.ScriptPlugin;
import com.xiovr.unibot.plugin.ScriptPluginRunnable;

public interface BotContext {
	public static final int OFFLINE_STATUS = 0x00;
	public static final int CONN_STATUS = 0x01;
	public static final int DISCONN_STATUS = 0x03;
	public static final int INWORLD_STATUS = 0x05;
	
	public static final int LOG_INFO_TYPE = 0x01;
	public static final int LOG_WARN_TYPE = 0x02;
	public static final int LOG_ERR_TYPE = 0x03;


	public int getBotId();
	public void setBotId(int botId);
	public int getConnectStage();
	public void setConnectStage(int stage);
	public BotEnvironment getBotEnvironment();
	public void setBotEnvironment(@NonNull BotEnvironment botEnvironment);
	public List<BotConnection> getServerConnections();
	public void addServerConnection(@NonNull BotConnection botConnection);
	public List<BotConnection> getClientConnections();
	public void addClientConnection(@NonNull BotConnection botConnection);
	// TODO Not implemented yet
	public void reconnect(int seconds);
	public boolean setStatus(int status);
	public int getStatus();
	public void sendToServer(Packet pck) throws InterruptedException;
	public void sendToClient(Packet pck) throws InterruptedException;
	public void sendToServerAndFlush(Packet pck) throws InterruptedException;
	public void sendToClientAndFlush(Packet pck) throws InterruptedException;
	public boolean sendMsgToBot(String name, String msg);
	public void setScript(ScriptPlugin script);
	public ScriptPlugin getScript();
	public void setCryptorPlugin(@NonNull CryptorPlugin cryptor);
	public CryptorPlugin getCryptorPlugin();
	@Deprecated
	public RingBufferPool<Packet> getWriteClientBuffer();
	@Deprecated
	public void setWriteClientBuffer(@NonNull RingBufferPool<Packet> sendBuf);
	@Deprecated
	public RingBufferPool<Packet> getWriteServerBuffer();
	@Deprecated
	public void setWriteServerBuffer(@NonNull RingBufferPool<Packet> sendBuf);
	public RingBufferPool<Packet> getReadBuffer();
	public void setReadBuffer(@NonNull RingBufferPool<Packet> sendBuf);
	public void setScriptPluginFacade(@NonNull ScriptPluginFacade botFacade);
	public ScriptPluginFacade getScriptPluginFacade();
	public void setBotSettings(@NonNull BotSettings botSettings);
	public BotSettings getBotSettings();
	public void setBotLogger(BotLogger botLogger);
	public BotLogger getBotLogger();
	public void log(int type, String msg);
}
