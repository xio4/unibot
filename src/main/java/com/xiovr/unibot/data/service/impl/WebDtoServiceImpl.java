package com.xiovr.unibot.data.service.impl;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.BotGameConfig;
import com.xiovr.unibot.bot.BotManager;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.bot.impl.BotEnvironmentImpl;
import com.xiovr.unibot.bot.impl.BotSettingsImpl;
import com.xiovr.unibot.data.dto.WebBotDto;
import com.xiovr.unibot.data.dto.WebEnvDto;
import com.xiovr.unibot.data.service.WebDtoService;
import com.xiovr.unibot.utils.exceptions.BotDoNotExistsException;
import com.xiovr.unibot.utils.exceptions.BotScriptCannotStopException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WebDtoServiceImpl implements WebDtoService {

	private static final Logger logger = LoggerFactory
			.getLogger(WebDtoServiceImpl.class);
	@Autowired
	BotManager botManager;
	@Autowired
	BotEnvironment botEnvironment;
	@Autowired
	BotGameConfig botGameConfig;

	private WebBotDto botToWebDto(BotContext bot) {
		WebBotDto botDto = new WebBotDto();
		final BotSettings botSettings = bot.getBotSettings();
		botDto.autoconnect = botSettings.getAutoConnect();
		botDto.autoconnect_interval = botSettings.getAutoConnectInterval();
		botDto.botId = bot.getBotId();
		botDto.charId = botSettings.getCharId();
		botDto.disabled = botSettings.getDisabled();
		botDto.logging = botSettings.getLogging();
		botDto.login = botSettings.getLogin();
		botDto.modif_logging = botSettings.getModifLogging();
		botDto.name = botSettings.getName();
		botDto.password = botSettings.getPassword();
		botDto.scriptPath = botSettings.getScriptPath();
		botDto.serverId = botSettings.getServerId();
		final int status = bot.getStatus();
		if (status == BotContext.INWORLD_STATUS)
			botDto.status = "Online";
		else if (status == BotContext.CONN_STATUS)
			botDto.status = "Connecting";
		else
			botDto.status = "Offline";
		final int type = botSettings.getType();
		if (type == BotSettings.INGAME_TYPE)
			botDto.type = "Ingame";
		else if (type == BotSettings.OUTGAME_TYPE)
			botDto.type = "Outgame";
		else if (type == BotSettings.PROXY_TYPE)
			botDto.type = "Proxy";
		return botDto;
	}

	private BotSettings webDtoToBotSettings(WebBotDto botDto) {
		BotSettings botSettings = new BotSettingsImpl();
		botSettings.setAutoConnect(botDto.autoconnect);
		botSettings.setAutoConnectInterval(botDto.autoconnect_interval);
		botSettings.setCharId(botDto.charId);
		botSettings.setDisabled(botDto.disabled);
		botSettings.setLogging(botDto.logging);
		botSettings.setLogin(botDto.login);
		botSettings.setModifLogging(botDto.modif_logging);
		botSettings.setName(botDto.name);
		botSettings.setPassword(botDto.password);
		botSettings.setScriptPath(botDto.scriptPath);
		botSettings.setServerId(botDto.serverId);

		if ("Proxy".equals(botDto.type))
			botSettings.setType(BotSettings.PROXY_TYPE);
		else if ("Ingame".equals(botDto.type))
			botSettings.setType(BotSettings.INGAME_TYPE);
		else if ("Outgame".equals(botDto.type))
			botSettings.setType(BotSettings.OUTGAME_TYPE);

		return botSettings;
	}

	private WebEnvDto envToWebDto(BotEnvironment botEnv) {
		WebEnvDto envDto = new WebEnvDto();
		envDto.cryptor_path = botEnv.getCryptorPath();

		StringBuilder strBuilderC = new StringBuilder();
		for (InetSocketAddress isa : botEnv.getClientAddresses()) {
			strBuilderC.append(isa.getHostString());
			strBuilderC.append(":");
			strBuilderC.append(isa.getPort());
			strBuilderC.append(";\n");
		}
		envDto.client = strBuilderC.toString();
		envDto.next_connection_interval = botEnv.getNextBotConnectionInterval();
		envDto.pathprefix = botEnv.getScriptsPathPrefix();
		envDto.port_range_max = botEnv.getPortRangeMax();
		envDto.port_range_min = botEnv.getPortRangeMin();
		envDto.raw_data = botEnv.getRawData();

		StringBuilder strBuilderS = new StringBuilder();
		for (InetSocketAddress isa : botEnv.getServerAddresses()) {
			strBuilderS.append(isa.getHostString());
			strBuilderS.append(":");
			strBuilderS.append(isa.getPort());
			strBuilderS.append(";\n");
		}
		envDto.server = strBuilderS.toString();
		envDto.update_interval = (int) botEnv.getUpdateInterval();
		return envDto;
	}

	private BotEnvironment webDtoToBotEnvironment(WebEnvDto envDto,
			BotEnvironment botEnv) {

		if (botEnv == null)
			botEnv = new BotEnvironmentImpl();
		Pattern pattern = Pattern
				.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\s*:\\s*[0-9]{2,5}");
		// in case you would like to ignore case sensitivity,
		// you could use this statement:
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(envDto.client);
		while (matcher.find()) {
			String[] rawAddrPort = matcher.group().split(":");
			InetSocketAddress addr = new InetSocketAddress(
					rawAddrPort[0].trim(), Integer.parseInt(rawAddrPort[1]
							.trim()));
			botEnv.addClientAddress(addr);
		}

		matcher = pattern.matcher(envDto.server);
		while (matcher.find()) {
			String[] rawAddrPort = matcher.group().split(":");
			InetSocketAddress addr = new InetSocketAddress(
					rawAddrPort[0].trim(), Integer.parseInt(rawAddrPort[1]
							.trim()));
			botEnv.addServerAddress(addr);
		}

		botEnv.setCryptorPath(envDto.cryptor_path);
		botEnv.setNextBotConnectionInterval(envDto.next_connection_interval);
		botEnv.setPortRangeMax(envDto.port_range_max);
		botEnv.setPortRangeMin(envDto.port_range_min);
		botEnv.setRawData(envDto.raw_data);
		botEnv.setScriptsPathPrefix(envDto.pathprefix);
		botEnv.setUpdateInterval(envDto.update_interval);
		return botEnv;
	}

	@Override
	public void addBot(WebBotDto botDto) {
		int typeId = strTypeToInt(botDto.type);
		List<BotContext> bots = botManager.getBots(typeId);
		for (BotContext bot : bots) {
			BotSettings botSettings = bot.getBotSettings();
			if (botDto.login.equals(botSettings.getLogin())
					&& botDto.name.equals(botSettings.getName())) {
				// Found bot copy
				return;
			}
		}
		BotSettings botSettings = webDtoToBotSettings(botDto);
		String configPath = botGameConfig.getAbsDirPath() + "/"
				+ BotSettings.PATH_PREFIX + "/"
				+ botManager.botConfigNameGenerator(botSettings);
		botGameConfig.saveSettings(botSettings, configPath,
				"Saved from frontend");
		try {
			botManager.createBot(typeId, configPath);
		} catch (BotDoNotExistsException | BotScriptCannotStopException e) {
			logger.debug("Error create bot with type={}", typeId);
		}
	}

	@Override
	public void deleteBot(String type, int id) {
		int typeId = strTypeToInt(type);
		try {
			botManager.disconnect(id, typeId);
			String configPath = botGameConfig.getAbsDirPath()
					+ "/"
					+ BotSettings.PATH_PREFIX
					+ "/"
					+ botManager.botConfigNameGenerator(botManager.getBot(id,
							typeId).getBotSettings());
			final File fn = new File(configPath);
			fn.delete();
			botManager.destroyBot(id, typeId);
			logger.debug("Bot with botId={} and type={} is deleted", id, type);
		} catch (BotDoNotExistsException e) {
			logger.warn("Cannot find bot with botId={} and type={}", id, type);
		}
	}

	@Override
	public void disconnectBot(String type, int id) {
		try {
			botManager.disconnect(id, strTypeToInt(type));
			botManager.resetBot(id, strTypeToInt(type));
			logger.debug("Bot with botId={} and type={} is disconnected", id,
					type);
		} catch (BotDoNotExistsException e) {
			logger.warn("Cannot find bot with botId={} and type={}", id, type);
		} catch (BotScriptCannotStopException e) {
			logger.error("Bot with botId={} and type={} cannot stop", id, type);
		}
	}

	@Override
	public void connectBot(String type, int id) {
		try {
			botManager.connect(id, strTypeToInt(type));

			logger.debug("Bot with botId={} and type={} is connected", id, type);
		} catch (BotDoNotExistsException e) {
			logger.warn("Cannot find bot with botId={} and type={}", id, type);
		}
	}

	@Override
	public void disconnectAllBots() {
		botManager.disconnectAllBots();
		logger.debug("All bots disconnected");
	}

	@Override
	public List<WebBotDto> getBots() {
		List<WebBotDto> botsDto = new ArrayList<WebBotDto>();
		List<BotContext> bots = botManager.getBotsAll();
		for (BotContext bot : bots) {
			botsDto.add(botToWebDto(bot));
		}
		return botsDto;
	}

	@Override
	public WebEnvDto getEnv() {
		WebEnvDto envDto = new WebEnvDto();
		envDto = envToWebDto(botEnvironment);
		return envDto;
	}

	@Override
	public void saveEnv(WebEnvDto envDto) {
		BotEnvironment botEnv = webDtoToBotEnvironment(envDto, null);
		botGameConfig.saveSettings(botEnv, botGameConfig.getAbsDirPath() + "/"
				+ BotEnvironment.ENVIRONMENT_CFG_FN, "Saved from frontend");
	}

	private int strTypeToInt(String type) {
		int typeId = BotSettings.OUTGAME_TYPE;
		if ("Outgame".equals(type)) {
			typeId = BotSettings.OUTGAME_TYPE;
		} else if ("Proxy".equals(type)) {
			typeId = BotSettings.PROXY_TYPE;
		} else {
			// FIXME Ingame bot is not implemented yet!
			return -1;
		}
		return typeId;
	}

	@SuppressWarnings("null")
	@Override
	public void updateBot(String type, int id, WebBotDto botDto) {
		try {
			BotContext botContext = botManager.getBot(id, strTypeToInt(type));
			BotSettings botSettings = webDtoToBotSettings(botDto);
			// Delete old config
			final File fn = new File(botGameConfig.getAbsDirPath()
					+ "/"
					+ BotSettings.PATH_PREFIX
					+ "/"
					+ botManager.botConfigNameGenerator(botContext
							.getBotSettings()));
			fn.delete();
			// Save new config
			botGameConfig.saveSettings(
					botSettings,
					botGameConfig.getAbsDirPath() + "/"
							+ BotSettings.PATH_PREFIX + "/"
							+ botManager.botConfigNameGenerator(botSettings),
					"Saved from frontend");
			String oldScriptPath = botContext.getBotSettings().getScriptPath();
			botContext.setBotSettings(botSettings);
			if (!"".equals(botSettings.getScriptPath())
					&& !oldScriptPath.equals(botSettings.getScriptPath())) {
				try {
					botManager.loadScript(id, strTypeToInt(type),
							botSettings.getScriptPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			logger.debug("Bot with botId={} and type={} is updated ", id, type);

		} catch (BotDoNotExistsException e) {
			logger.warn("Cannot find bot with botId={} and type={}", id, type);
		}
	}
}
