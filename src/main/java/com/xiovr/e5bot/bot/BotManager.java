package com.xiovr.e5bot.bot;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.e5bot.bot.analize.AnalizeManager;
import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.bot.network.ConnectionFactory;
import com.xiovr.e5bot.plugin.CryptorPlugin;
import com.xiovr.e5bot.plugin.PluginLoader;
import com.xiovr.e5bot.plugin.ScriptPlugin;
import com.xiovr.e5bot.utils.exceptions.BotDoNotExistsException;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.RingBufferPool;

public interface BotManager {
	public static final int BOT_MAX_COUNT = 1000;

	public BotEnvironment getBotEnvironment();
	public void setBotEnvironment(@NonNull BotEnvironment botEnvironment);
	public ConnectionFactory getConnectionFactory();
	public void setConnectionFactory(@NonNull ConnectionFactory connContext);
	public void setBotGameConfig(@NonNull BotGameConfig botGameConfig);
	public BotGameConfig getBotGameConfig();
	public void setBotLogger(@NonNull BotLogger botLogger);
	public BotLogger getBotLogger();
	public void clear();
	public BotContext createBot(int botType, @NonNull String configName) throws BotDoNotExistsException;
	public void destroyBot(int botId, int botType) throws BotDoNotExistsException;
	public BotContext getBot(int botId, int botType) throws BotDoNotExistsException;
	public void loadScript(int botId, int botType, @NonNull String scriptPath) throws Exception;
	public void removeScript(int botId, int botType) throws BotDoNotExistsException;
	@Deprecated
	public void loadBots();
	public List<BotContext> getBots(int botType);
	public int botsCount(int botType) throws BotDoNotExistsException;
	public void connect(int botId, int botType) throws BotDoNotExistsException;
	public void disconnect(int botId, int botType) throws BotDoNotExistsException;
	public BotMessageTransferRunnable getBotMessageTransfer();
	public void setBotMessageTransfer(@NonNull BotMessageTransferRunnable bmtt);
	public void setPluginLoader(@NonNull PluginLoader pluginLoader);
	public PluginLoader getPluginLoader();

}
