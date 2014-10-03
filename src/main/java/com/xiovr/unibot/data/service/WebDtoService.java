package com.xiovr.unibot.data.service;

import java.util.List;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.data.dto.WebBotDto;
import com.xiovr.unibot.data.dto.WebEnvDto;

public interface WebDtoService {
//	public WebBotDto botToWebDto(BotContext bot);
//	public BotSettings webDtoToBotSettings(WebBotDto botDto);
//	public WebEnvDto envToWebDto(BotEnvironment botEnv);
//	public BotEnvironment webDtoToBotEnvironment(WebEnvDto envDto,
//			BotEnvironment botEnv);
	
	public void addBot(WebBotDto botDto);
	public void deleteBot(String type, int id);
	public void disconnectBot(String type, int id);
	public void connectBot(String type, int id);
	public void disconnectAllBots();
	public List<WebBotDto> getBots();
	public WebEnvDto getEnv();
	public void saveEnv(WebEnvDto envDto);
	public void updateBot(String type, int id, WebBotDto botDto);
	
}
