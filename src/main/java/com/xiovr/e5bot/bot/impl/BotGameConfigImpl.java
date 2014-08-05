package com.xiovr.e5bot.bot.impl;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xiovr.e5bot.bot.BotEnvironment;
import com.xiovr.e5bot.bot.BotGameConfig;
import com.xiovr.e5bot.bot.BotSettings;
import com.xiovr.e5bot.utils.BotUtils;

@Component
public class BotGameConfigImpl implements BotGameConfig {
	public final String DIR_PATH;
	private static final Logger logger = LoggerFactory
			.getLogger(BotGameConfigImpl.class);

	public BotGameConfigImpl() {
		super();
		DIR_PATH = getClass().getProtectionDomain().getCodeSource()
				.getLocation().toString().substring(6);
	}


	@Override
	public void saveBotSettings(BotSettings settings, String botFn,
			String comments) {
		try {
			File fn = new File("/" + DIR_PATH + "/" + botFn);
			Properties props = new Properties();
			props.setProperty("bot.type", String.valueOf(settings.getType()));
			props.setProperty("bot.login", settings.getLogin());
			props.setProperty("bot.password", settings.getPassword());
			props.setProperty("bot.name", settings.getName());
			props.setProperty("bot.serverId",
					String.valueOf(settings.getServerId()));
			props.setProperty("bot.autoconnect",
					String.valueOf(settings.isAutoConnect()));
			props.setProperty("bot.autoconnect_interval",
					String.valueOf(settings.getAutoConnectInterval()));
			props.setProperty("bot.disabled",
					String.valueOf(settings.isDisabled()));
			props.setProperty("bot.logging",
					String.valueOf(settings.isLogging()));
			props.setProperty("bot.modif_logging",
					String.valueOf(settings.isModifLogging()));
			BotUtils.saveProperties(fn, props, comments);
		} catch (IOException e) {
			logger.error("Error save file " + botFn);
		}
	}

	@Override
	public Properties createBotSettings(String botFn,
			String comments) {
		try {
			File fn = new File("/" + DIR_PATH + "/" + botFn);
			Properties props = new Properties();

			props.setProperty("bot.type",
					String.valueOf(BotSettings.OUTGAME_TYPE));
			props.setProperty("bot.login", "");
			props.setProperty("bot.password", "");
			props.setProperty("bot.name", "");
			props.setProperty("bot.serverId", String.valueOf(0));
			props.setProperty("bot.client_proxy", String.valueOf(false));
			props.setProperty("bot.autoconnect", String.valueOf(false));
			props.setProperty("bot.autoconnect_interval", String.valueOf(10));
			props.setProperty("bot.disabled", String.valueOf(false));
			props.setProperty("bot.logging", String.valueOf(false));
			props.setProperty("bot.modif_logging", String.valueOf(false));

			BotUtils.saveProperties(fn, props, comments);
			return props;
		} catch (IOException e) {
			logger.error("Error create file " + botFn);

		}
		return null;
	}

	@Override
	public void loadBotSettings(BotSettings botSettings, String botFn) {
		File fn = new File("/" + DIR_PATH + "/" + botFn);
		Properties props = null;
		try {
			props = BotUtils.loadProperties(fn);

			// botEnvironment.setClientIp((props.getProperty("client.ip",
			// "127.0.0.1"));
		} catch (IOException e) {
			// e.printStackTrace();
			logger.warn("Can't load settings /" + DIR_PATH + "/"
					+ botFn + ". Create new settings file.");
			props = createEnvironmentProps(fn);

		}
		if (props == null) {
			logger.error("Error create bot settings. Terminate.");
			return;
		}

		botSettings.setType(Integer.parseInt(props.getProperty("bot.type",
				String.valueOf(BotSettings.OUTGAME_TYPE))));
		botSettings.setLogin(props.getProperty("bot.login", ""));
		botSettings.setPassword(props.getProperty("bot.password", ""));
		botSettings.setName(props.getProperty("bot.name", ""));
		botSettings.setServerId(Integer.parseInt(props.getProperty(
				"bot.serverId", "0")));
		botSettings.setAutoConnect(Boolean.parseBoolean(props.getProperty(
				"bot.autoconnect", "false")));
		botSettings.setAutoConnectInterval(Integer.parseInt(props.getProperty(
				"bot.autoconnect_interval", "10")));
		botSettings.setDisabled(Boolean.parseBoolean(props.getProperty(
				"bot.disabled", "false")));
		botSettings.setLogging(Boolean.parseBoolean(props.getProperty(
				"bot.logging", "false")));
		botSettings.setModifLogging(Boolean.parseBoolean(props.getProperty(
				"bot.modif_logging", "false")));
		logger.info("Bot settings " + botFn + " has been loaded");
	}
	
	private Properties createEnvironmentProps(File fn) {
		try {
			Properties props = new Properties();

			props.setProperty("client.ip", DEFAULT_CLIENT_IP);
			props.setProperty("server.ip", DEFAULT_SERVER_IP);
			props.setProperty("client.port",
					String.valueOf(DEFAULT_CLIENT_PORT));
			props.setProperty("server.port",
					String.valueOf(DEFAULT_SERVER_PORT));
			props.setProperty("script.update_interval", "100");
			props.setProperty("bot.next_connection_interval", "5");
			props.setProperty("client.proxy", "false");
			props.setProperty("bot.raw_data", "false");

			BotUtils.saveProperties(fn, props, "Autogeneratied file. Bot v"
					+ VERSION);

			return props;
		} catch (IOException e) {
			logger.error("Error create file " + fn.getName());

		}
		return null;
	}

	@Override
	public void savePropsFromBotEnvironment(BotEnvironment botEnvironment) {
		try {

			File fn = new File("/" + DIR_PATH + "/" + ENVIRONMENT_CFG_FN);
			Properties props = new Properties();

			props.setProperty("client.ip", botEnvironment.getClientAddress()
					.getAddress().getHostAddress());
			props.setProperty("server.ip", botEnvironment.getServerAddress()
					.getAddress().getHostAddress());
			props.setProperty("client.port",
					String.valueOf(botEnvironment.getClientAddress().getPort()));
			props.setProperty("server.port",
					String.valueOf(botEnvironment.getServerAddress().getPort()));

			props.setProperty("script.update_interval",
					String.valueOf(botEnvironment.getUpdateInterval()));
			props.setProperty("bot.next_connection_interval", String
					.valueOf(botEnvironment.getNextBotConnectionInterval()));
			props.setProperty("client.proxy",
					String.valueOf(botEnvironment.isProxy()));
			props.setProperty("bot.raw_data",
					String.valueOf(botEnvironment.isRawData()));
			BotUtils.saveProperties(fn, props, "Autogeneratied file. Bot v"
					+ VERSION);

		} catch (IOException e) {

		}
	}

	@Override
	public void loadPropsToBotEnvironment(BotEnvironment botEnvironment) {
		File fn = new File("/" + DIR_PATH + "/" + ENVIRONMENT_CFG_FN);
		Properties props = null;
		try {
			props = BotUtils.loadProperties(fn);

			// botEnvironment.setClientIp((props.getProperty("client.ip",
			// "127.0.0.1"));
		} catch (IOException e) {
			// e.printStackTrace();
			logger.warn("Can't load config /" + DIR_PATH + "/"
					+ ENVIRONMENT_CFG_FN + ". Create new config.");
			props = createEnvironmentProps(fn);

		}
		if (props == null) {
			logger.error("Error create properties. Terminate load.");
			return;
		}
		InetSocketAddress sAddr = new InetSocketAddress(props.getProperty(
				"client.ip", DEFAULT_CLIENT_IP),
				Integer.parseInt(props.getProperty("client.port",
						String.valueOf(DEFAULT_CLIENT_PORT))));
		botEnvironment.setClientAddress(sAddr);
		InetSocketAddress cAddr = new InetSocketAddress(props.getProperty(
				"server.ip", DEFAULT_SERVER_IP),
				Integer.parseInt(props.getProperty("server.port",
						String.valueOf(DEFAULT_SERVER_PORT))));
		botEnvironment.setServerAddress(cAddr);

		botEnvironment.setUpdateInterval(Long.parseLong(props.getProperty(
				"script.update_interval", "100")));
		botEnvironment.setNextBotConnectionInterval(Integer.parseInt(props
				.getProperty("bot.next_connection_interval", "5")));

		botEnvironment.setProxy(Boolean.parseBoolean(props.getProperty(
				"client.proxy", "false")));
		
        botEnvironment.setRawData(Boolean.parseBoolean(props.getProperty(
				"bot.raw_data", "false")));
		logger.info("Environment config loaded");
	}
}
