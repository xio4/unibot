package com.xiovr.e5bot.bot;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.RingBufferPool;
import com.xiovr.e5bot.plugin.CryptorCommand;
import com.xiovr.e5bot.plugin.CryptorPlugin;
import com.xiovr.e5bot.plugin.ScriptPlugin;
import com.xiovr.e5bot.plugin.ScriptPluginRunnable;

public interface BotContext {
	public static final int OFFLINE_STATUS = 0x00;
	public static final int CONN_TO_LS_STATUS = 0x01;
	public static final int LOGIN_STATUS = 0x02;
	public static final int DISCONN_FROM_LS_STATUS = 0x03;
	public static final int CONN_TO_GS_STATUS = 0x04;
	public static final int INWORLD_STATUS = 0x05;
	public static final int DISCONN_FROM_GS_STATUS = 0x06;
	
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
	public void addServerConnectionStage(@NonNull BotConnection botConnection);
	public List<BotConnection> getClientConnections();
	public void addClientConnectionStage(@NonNull BotConnection botConnection);
	// TODO Not implemented yet
	public void reconnect(int seconds);
	public boolean setStatus(int status);
	public int getStatus();
	public void sendToServer(Packet pck) throws InterruptedException;
	public void sendToClient(Packet pck) throws InterruptedException;
	public void sendToServerAndFlush(Packet pck) throws InterruptedException;
	public void sendToClientAndFlush(Packet pck) throws InterruptedException;
	public boolean sendMsgToBot(String name, String msg);
	public void setScript(@NonNull ScriptPlugin script);
	public ScriptPlugin getScript();
	public void setCryptorPlugin(@NonNull CryptorPlugin cryptor);
	public CryptorPlugin getCryptorPlugin();
	public CryptorCommand getCryptorCommand();
	public void setCryptorCommand(@NonNull CryptorCommand cryptorCommand);
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
	public void setBotThreadFacade(@NonNull BotThreadFacade botThread);
	public BotThreadFacade getBotThreadFacade();
	public void setBotSettings(@NonNull BotSettings botSettings);
	public BotSettings getBotSettings();
	public void setBotLogger(BotLogger botLogger);
	public BotLogger getBotLogger();
	public void log(int type, String msg);
}
