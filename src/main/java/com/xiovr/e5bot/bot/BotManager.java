package com.xiovr.e5bot.bot;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.analize.AnalizeManager;
import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.bot.network.ConnectionContext;
import com.xiovr.e5bot.plugin.CryptorPlugin;
import com.xiovr.e5bot.plugin.PluginLoader;
import com.xiovr.e5bot.plugin.ScriptPlugin;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.RingBufferPool;

public interface BotManager {
	public static final int BOT_MAX_COUNT = 1000;
	public BotEnvironment getBotEnvironment();
	public void setBotEnvironment(BotEnvironment botEnvironment);
	public ConnectionContext getConnectionContext();
	public void setConnectionContext(@NonNull ConnectionContext connContext);
	public void clear();
	public @NonNull BotContext createBot();
	public void destroyBot(int botId);
	public BotContext getBot(int botId);
	public boolean loadScript(int botId, ScriptPlugin script);
	public void stopScript(int botId);
	public void startScript(int botId);
	public void removeScript(int botId);
	public void loadBots();
	public List<BotContext> getBots();
	public int botsCount();
	public void connect(int botId);
	public void disconnect(int botId);
	public BotMessageTransferRunnable getBotMessageTransfer();
	public void setBotMessageTransfer(@NonNull BotMessageTransferRunnable bmtt);
	public void setPluginLoader(@NonNull PluginLoader pluginLoader);
	public PluginLoader getPluginLoader();
	public RingBufferPool<Packet> getSendBuffer();
	public void setSendBuffer(RingBufferPool<Packet> sendBuf);

}
