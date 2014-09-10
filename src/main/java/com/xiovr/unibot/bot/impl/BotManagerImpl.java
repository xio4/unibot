/**
 * Copyright (c) 2014 xio4
 * Universal bot for lineage-like games (Archeage, Lineage2 etc)
 *
 * This file is part of Unibot.
 *
 * Unibot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.xiovr.unibot.bot.impl;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.BotGameConfig;
import com.xiovr.unibot.bot.BotLogger;
import com.xiovr.unibot.bot.BotManager;
import com.xiovr.unibot.bot.BotMessageTransferRunnable;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.bot.ScriptPluginFacade;
import com.xiovr.unibot.bot.network.ConnectionFactory;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.bot.packet.impl.RingBufferPacketPoolImpl;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.PluginLoader;
import com.xiovr.unibot.plugin.ScriptPlugin;
import com.xiovr.unibot.plugin.ScriptPluginRunnable;
import com.xiovr.unibot.plugin.impl.ScriptPluginRunnableImpl;
import com.xiovr.unibot.utils.exceptions.BotDoNotExistsException;
import com.xiovr.unibot.utils.exceptions.BotScriptCannotStopException;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BotManagerImpl implements BotManager {
	@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
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

	private BotContext getBotContextByIdAndType(int botId, int botType)
			throws BotDoNotExistsException {
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
			throws BotDoNotExistsException, BotScriptCannotStopException {
		BotContext botContext = new BotContextImpl();
		botContext.setManager(this);
		botContext.setBotEnvironment(botEnvironment);
		BotSettings botSettings = new BotSettingsImpl();
		botGameConfig.loadSettings(botSettings, configName);
		botContext.setBotSettings(botSettings);
		CryptorPlugin cp = pluginLoader.createCryptorPlugin();
		cp.init(botContext);
		botContext.setCryptorPlugin(cp);
		RingBufferPool<Packet> readBuf = new RingBufferPacketPoolImpl();
		botContext.setReadBuffer(readBuf);

		if (botType == BotSettings.PROXY_TYPE) {
			for (int i = 0; i < botEnvironment.getClientAddresses().size(); ++i) {
				connectionFactory.createBotConnectionClient(botContext, i);
			}
		}

		for (int i = 0; i < botEnvironment.getServerAddresses().size(); ++i) {
			connectionFactory.createBotConnectionServer(botContext, i);
		}

		ScriptPluginFacade spf = new ScriptPluginFacadeImpl();

		botContext.setScriptPluginFacade(spf);
		List<BotContext> bots = null;
		bots = getBotsListByType(botType);
		bots.add(botContext);
		botContext.setBotId(bots.size() - 1);

		resetBot(botContext.getBotId(), botType);

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
			throws Exception {

		BotContext botContext = null;
		botContext = getBotContextByIdAndType(botId, botType);

		if (botContext.getScript() != null) {
			pluginLoader.unloadPlugin(botContext.getScript().getClass());
			botContext.setScript(null);
		}
		ScriptPlugin script = pluginLoader.createScriptPlugin(scriptPath);
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

		// List<BotContext> bots = null;

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
	public void disconnect(int botId, int botType)
			throws BotDoNotExistsException {
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

	private Thread createScriptThreadBot(BotContext botContext)
			throws BotScriptCannotStopException {
		ScriptPluginFacade spf = botContext.getScriptPluginFacade();
		Thread oldSpt = spf.getScriptPluginThread();
		if (oldSpt != null) {
			try {
				if (oldSpt.isAlive()) {
					oldSpt.interrupt();
				}
				oldSpt.join(100);
				if (oldSpt.isAlive()) {
					throw new BotScriptCannotStopException(
							botContext.getBotId(), botContext.getBotSettings()
									.getType());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		ScriptPluginRunnable spr = new ScriptPluginRunnableImpl(botContext);
		Thread newSpt = new Thread(spr);
		spf.setScriptPluginRunnable(spr);
		spf.setScriptPluginThread(newSpt);

		return newSpt;
	}

	@Override
	public void resetBot(int botId, int botType)
			throws BotDoNotExistsException, BotScriptCannotStopException {
		BotContext botContext = getBot(botId, botType);
		botContext.setConnectStage(0);
		Thread spt = createScriptThreadBot(botContext);

		try {
			// Start bot thread
			spt.start();
			// Wait while thread is started
			while (!spt.isAlive()) {
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
