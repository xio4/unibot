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
import com.xiovr.e5bot.plugin.PluginLoader;
import com.xiovr.e5bot.plugin.ScriptPlugin;
import com.xiovr.e5bot.plugin.ScriptPluginRunnable;
import com.xiovr.e5bot.plugin.impl.ScriptPluginRunnableImpl;

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

	@SuppressWarnings("null")
	@Override
	public @NonNull BotContext createBot(int botType, @NonNull String configName) {
		BotContext botContext = new BotContextImpl();
		botContext.setBotEnvironment(botEnvironment);
		BotSettings botSettings = new BotSettingsImpl();
		botGameConfig.loadSettings(botSettings, configName);
		botContext.setBotSettings(botSettings);
		botContext.setCryptorPlugin(pluginLoader.createCryptorPlugin());
		RingBufferPool<Packet> readBuf = new RingBufferPacketPoolImpl();
		botContext.setReadBuffer(readBuf);

		if (botType == BotSettings.INGAME_TYPE) {
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

		switch (botType) {
		case BotSettings.INGAME_TYPE:
			ingameBots.add(botContext);
			botContext.setBotId(ingameBots.size() - 1);
			break;
		case BotSettings.OUTGAME_TYPE:
			outgameBots.add(botContext);
			botContext.setBotId(outgameBots.size() - 1);
			break;
		case BotSettings.PROXY_TYPE:
			proxyBots.add(botContext);
			botContext.setBotId(proxyBots.size() - 1);
			break;
		default:
		}

		// Start bot thread
		spt.start();
		return botContext;
	}

	@Override
	public void destroyBot(int botId, int botType) {
		BotContext botContext = null;
		;
		switch (botType) {
		case BotSettings.INGAME_TYPE:
			botContext = ingameBots.get(botId);
			ingameBots.remove(botId);
			break;
		case BotSettings.OUTGAME_TYPE:
			botContext = outgameBots.get(botId);
			outgameBots.remove(botId);
			break;
		case BotSettings.PROXY_TYPE:
			botContext = proxyBots.get(botId);
			proxyBots.remove(botId);
			break;
		default:
		}
		if (botContext == null)
			return;
		ScriptPlugin script = botContext.getScript();
		if (script != null)
			botContext.getScript().dispose();
		botContext.getScriptPluginFacade().getScriptPluginThread().interrupt();
		// FIXME Need remove bot config!
	}

	@Override
	public BotContext getBot(int botId, int botType) {
		switch (botType) {
		case BotSettings.INGAME_TYPE:
			return ingameBots.get(botId);
		case BotSettings.OUTGAME_TYPE:
			return outgameBots.get(botId);
		case BotSettings.PROXY_TYPE:
			return proxyBots.get(botId);
		default:
			return null;
		}
	}

	@Override
	public void loadScript(int botId, int botType, @NonNull String scriptPath)
			throws Exception {
		ScriptPlugin script = pluginLoader.createScriptPlugin(scriptPath);
		BotContext botContext = null;
		switch (botType) {
		case BotSettings.INGAME_TYPE:
			botContext = ingameBots.get(botId);

			break;
		case BotSettings.OUTGAME_TYPE:

			botContext = outgameBots.get(botId);
			break;
		case BotSettings.PROXY_TYPE:
			botContext = proxyBots.get(botId);
			break;
		default:
		}
		if (botContext == null)
			throw new RuntimeException("Can't find botContext by botId="
					+ botId);
		botContext.setScript(script);
	}

	@Override
	public void removeScript(int botId, int botType) {
		BotContext botContext = null;
		switch (botType) {
		case BotSettings.INGAME_TYPE:
			botContext = ingameBots.get(botId);

			break;
		case BotSettings.OUTGAME_TYPE:

			botContext = outgameBots.get(botId);
			break;
		case BotSettings.PROXY_TYPE:
			botContext = proxyBots.get(botId);
			break;
		default:
		}
		if (botContext == null)
			return;
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
	public int botsCount(int botType) {
		switch (botType) {
		case BotSettings.INGAME_TYPE:
			return ingameBots.size();
		case BotSettings.OUTGAME_TYPE:
			return outgameBots.size();
		case BotSettings.PROXY_TYPE:
			return proxyBots.size();
		default:
			throw new RuntimeException("botType " + botType
					+ " is not recognized");
		}
	}

	@SuppressWarnings("null")
	@Override
	public void connect(int botId, int botType) {

		switch (botType) {
		case BotSettings.INGAME_TYPE:
			// It works only outgame bot!
			break;
		case BotSettings.OUTGAME_TYPE:
			BotContext botContext = ingameBots.get(botId);
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
	public void disconnect(int botId, int botType) {
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
			return;
		}
		for (int i = 0; i < botEnvironment.getServerAddresses().size(); ++i) {
			bots.get(botId).getServerConnections().get(i).disconnect();
		}
		for (int i = 0; i < botEnvironment.getClientAddresses().size(); ++i) {
			bots.get(botId).getClientConnections().get(i).disconnect();
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
