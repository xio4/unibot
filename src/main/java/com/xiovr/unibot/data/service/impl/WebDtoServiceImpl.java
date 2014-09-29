package com.xiovr.unibot.data.service.impl;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.bot.impl.BotSettingsImpl;
import com.xiovr.unibot.data.dto.WebBotDto;
import com.xiovr.unibot.data.service.WebDtoService;

public class WebDtoServiceImpl implements WebDtoService {

	@Override
	public WebBotDto botToWebDto(BotContext bot) {
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

	@Override
	public BotSettings webDtoToBotSettings(WebBotDto botDto) {
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

}
