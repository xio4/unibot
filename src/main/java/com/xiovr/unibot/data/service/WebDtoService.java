package com.xiovr.unibot.data.service;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.data.dto.WebBotDto;

public interface WebDtoService {
	public WebBotDto botToWebDto(BotContext bot);
	public BotSettings webDtoToBotSettings(WebBotDto botDto);
}
