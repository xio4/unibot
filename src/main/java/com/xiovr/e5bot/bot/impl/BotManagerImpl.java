package com.xiovr.e5bot.bot.impl;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotEnvironment;
import com.xiovr.e5bot.bot.BotGameConfig;
import com.xiovr.e5bot.bot.BotLogger;
import com.xiovr.e5bot.bot.BotMessageTransferRunnable;
import com.xiovr.e5bot.bot.BotSettings;
import com.xiovr.e5bot.bot.BotManager;
import com.xiovr.e5bot.bot.ScriptPluginFacade;
import com.xiovr.e5bot.bot.network.ConnectionFactory;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.RingBufferPool;
import com.xiovr.e5bot.bot.packet.impl.RingBufferPacketPoolImpl;
import com.xiovr.e5bot.plugin.CryptorPlugin;
import com.xiovr.e5bot.plugin.PluginLoader;
import com.xiovr.e5bot.plugin.ScriptPlugin;
import com.xiovr.e5bot.plugin.ScriptPluginRunnable;
import com.xiovr.e5bot.plugin.impl.ScriptPluginRunnableImpl;
import com.xiovr.e5bot.utils.exceptions.BotDoNotExistsException;
import com.xiovr.e5bot.utils.exceptions.BotScriptNotFoundException;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BotManagerImpl implements BotManager {
	private Logger logger = LoggerFactory.getLogger(BotManagerImpl.class);
	private List<BotContext> proxyBots;
	private List<BotContext> ingameBots;
	private List<BotContext> outgameBots;
	private BotEnvironment botEnvironment;
	private ConnectionFactory connectionFactory;
	private BotMessageTransferRunnable botMessageTransferRunnable;
	private PluginLoader pluginLoader;
	private BotGameConfig botGameConfig;
	private BotLogger botLogger;

	public BotManagerImpl() {
		proxyBots = new CopyOnWriteArrayList<BotContext>();
		ingameBots = new CopyOnWriteArrayList<BotContext>();
		outgameBots = new CopyOnWriteArrayList<BotContext>();
	}

	@Override
	public BotEnvironment getBotEnvironment() {
		return this.botEnvironment;
	}

	@Override
	public void setBotEnvironment(@NonNull BotEnvironment botEnvironment) {
		this.botEnvironment = botEnvironment;

	}

	@Override
	public ConnectionFactory getConnectionFactory() {
		return this.connectionFactory;
	}

	@Override
	public void setConnectionFactory(@NonNull ConnectionFactory connContext) {
		this.connectionFactory = connContext;

	}

	@Override
	public void clear() {
		ingameBots.clear();
		outgameBots.clear();
		proxyBots.clear();
	}

	@Override
	public BotMessageTransferRunnable getBotMessageTransfer() {
		return this.botMessageTransferRunnable;
	}

	@Override
	public void setBotMessageTransfer(@NonNull BotMessageTransferRunnable bmtt) {
		this.botMessageTransferRunnable = bmtt;
	}

	@Override
	public void setPluginLoader(@NonNull PluginLoader pluginLoader) {
		this.pluginLoader = pluginLoader;
	}

	@Override
	public PluginLoader getPluginLoader() {
		return this.pluginLoader;
	}

	private List<BotContext> getBotsListByType(int botType)
			throws BotDoNotExistsException {
		BotContext botContext = null;
		List<BotContext> bots = null;
		switch (botType) {
		case BotSettings.INGAME_TYPE:
			bots = ingameBots;
			break;
		case BotSettings.OUTGAME_TYPE:
			bots = outgameBots;
			break;
		case BotSettings.PROXY_TYPE:
			bots = proxyBots;
			break;
		default:
			bots = null;
		}
		if (bots == null)
			throw new BotDoNotExistsException(-1, botType);
		return bots;
	}
	
	private BotContext getBotContextByIdAndType(int botId, int botType) throws BotDoNotExistsException
	{
		BotContext botContext = null;
        List<BotContext> bots = null;
		bots = getBotsListByType(botType);	
        if (bots.size() <= botId)
			throw new BotDoNotExistsException(botId, botType);
		botContext = bots.get(botId);	
		if (botContext == null)
			throw new BotDoNotExistsException(botId, botType);
		
		return botContext;
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull BotContext createBot(int botType, @NonNull String configName)
			throws BotDoNotExistsException {
		BotContext botContext = new BotContextImpl();
		botContext.setBotEnvironment(botEnvironment);
		BotSettings botSettings = new BotSettingsImpl();
		botGameConfig.loadSettings(botSettings, configName);
		botContext.setBotSettings(botSettings);
		final CryptorPlugin cp = pluginLoader.createCryptorPlugin();
		cp.init(botContext);
		botContext.setCryptorPlugin(cp);
		RingBufferPool<Packet> readBuf = new RingBufferPacketPoolImpl();
		botContext.setReadBuffer(readBuf);

		if (botType == BotSettings.PROXY_TYPE) {
			for (int i = 0; i < botEnvironment.getClientAddresses().size(); ++i) {
				connectionFactory.createBotConnectionClient(botContext);
			}
		}

		for (int i = 0; i < botEnvironment.getServerAddresses().size(); ++i) {
			connectionFactory.createBotConnectionServer(botContext);
		}

		botContext.setConnectStage(0);

		ScriptPluginFacade spf = new ScriptPluginFacadeImpl();
		ScriptPluginRunnable spr = new ScriptPluginRunnableImpl(botContext);
		Thread spt = new Thread(spr);
		spf.setScriptPluginRunnable(spr);
		spf.setScriptPluginThread(spt);

		botContext.setScriptPluginFacade(spf);
		List<BotContext> bots = null;
		bots = getBotsListByType(botType);
		bots.add(botContext);
		botContext.setBotId(bots.size() - 1);

		// Start bot thread
		spt.start();
		return botContext;
	}

	@Override
	public void destroyBot(int botId, int botType)
			throws BotDoNotExistsException {
		BotContext botContext = null;
		List<BotContext> bots = null;
		bots = getBotsListByType(botType);
		if (bots.size() <= botId)
			throw new BotDoNotExistsException(botId, botType);
		botContext = bots.get(botId);
		if (botContext == null)
			throw new BotDoNotExistsException(botId, botType);
		bots.remove(botId);
		final ScriptPlugin script = botContext.getScript();
		if (script != null)
			script.dispose();
		botContext.getScriptPluginFacade().getScriptPluginThread().interrupt();
		// FIXME Need remove bot config!
	}

	@Override
	public BotContext getBot(int botId, int botType)
			throws BotDoNotExistsException {
		return getBotContextByIdAndType(botId, botType);
	}

	@Override
	public void loadScript(int botId, int botType, @NonNull String scriptPath)
			throws BotDoNotExistsException, BotScriptNotFoundException {
		ScriptPlugin script = pluginLoader.createScriptPlugin(scriptPath);

		BotContext botContext = null;
		botContext = getBotContextByIdAndType(botId, botType);
		botContext.setScript(script);
		botContext.getBotSettings().setScriptPath(scriptPath);
	}

	@Override
	public void removeScript(int botId, int botType)
			throws BotDoNotExistsException {

		BotContext botContext = null;
		botContext = getBotContextByIdAndType(botId, botType);
		final ScriptPlugin script = botContext.getScript();
		if (script != null)
			script.dispose();
		botContext.setScript(null);
		botContext.getBotSettings().setScriptPath("");
	}

	@Override
	public List<BotContext> getBots(int botType) {
		switch (botType) {
		case BotSettings.INGAME_TYPE:
			return ingameBots;
		case BotSettings.OUTGAME_TYPE:
			return outgameBots;
		case BotSettings.PROXY_TYPE:
			return proxyBots;
		default:
			return null;
		}
	}

	@Override
	public int botsCount(int botType) throws BotDoNotExistsException {
		switch (botType) {
		case BotSettings.INGAME_TYPE:
			return ingameBots.size();
		case BotSettings.OUTGAME_TYPE:
			return outgameBots.size();
		case BotSettings.PROXY_TYPE:
			return proxyBots.size();
		default:
			throw new BotDoNotExistsException(-1, botType);
		}
	}

	@SuppressWarnings("null")
	@Override
	public void connect(int botId, int botType) throws BotDoNotExistsException {

//		List<BotContext> bots = null;

		BotContext botContext = null;
		botContext = getBotContextByIdAndType(botId, botType);

		switch (botType) {
		case BotSettings.INGAME_TYPE:
			// It works only outgame bot!
			break;
		case BotSettings.OUTGAME_TYPE:
			InetSocketAddress address = botEnvironment.getServerAddresses()
					.get(botContext.getConnectStage());
			botContext.getServerConnections().get(botContext.getConnectStage())
					.connect(address);
			break;
		case BotSettings.PROXY_TYPE:
			// It works only outgame bot!
			break;
		default:
		}
	}

	@Override
	public void disconnect(int botId, int botType) throws BotDoNotExistsException {
		BotContext botContext = null;
		botContext = getBotContextByIdAndType(botId, botType);
		for (int i = 0; i < botEnvironment.getServerAddresses().size(); ++i) {
			botContext.getServerConnections().get(i).disconnect();
		}
		for (int i = 0; i < botEnvironment.getClientAddresses().size(); ++i) {
			botContext.getClientConnections().get(i).disconnect();
		}
	}

	@Override
	public void setBotGameConfig(@NonNull BotGameConfig botGameConfig) {
		this.botGameConfig = botGameConfig;
	}

	@Override
	public BotGameConfig getBotGameConfig() {
		return this.botGameConfig;
	}

	@Override
	public void loadBots() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBotLogger(@NonNull BotLogger botLogger) {
		this.botLogger = botLogger;
	}

	@Override
	public BotLogger getBotLogger() {
		return this.botLogger;
	}

}
