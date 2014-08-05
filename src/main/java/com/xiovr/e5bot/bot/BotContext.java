package com.xiovr.e5bot.bot;

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
	public BotEnvironment getBotEnvironment();
	public void setBotEnvironment(@NonNull BotEnvironment botEnvironment);
	public BotConnection getServerConnection();
	public void setServerConnection(BotConnection botConnection);
	public BotConnection getClientConnection();
	public void setClientConnection(BotConnection botConnection);
	// TODO Not implemented yet
	public void reconnect(int seconds);
	public void setStatus(int status);
	public int getStatus();
	public void sendToServer(Packet pck) throws InterruptedException;
	public void sendToClient(Packet pck) throws InterruptedException;
	public boolean sendMsgToBot(String name, String msg);
	public void setScript(@NonNull ScriptPlugin script);
	public ScriptPlugin getScript();
	public void setCryptorPlugin(@NonNull CryptorPlugin cryptor);
	public CryptorPlugin getCryptorPlugin();
	public CryptorCommand getCryptorCommand();
	public void setCryptorCommand(@NonNull CryptorCommand cryptorCommand);
	public RingBufferPool<Packet> getWriteClientBuffer();
	public void setWriteClientBuffer(@NonNull RingBufferPool<Packet> sendBuf);
	public RingBufferPool<Packet> getWriteServerBuffer();
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
