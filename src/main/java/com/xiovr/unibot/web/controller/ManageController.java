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
package com.xiovr.unibot.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiovr.unibot.bot.BotAutoconnection;
import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.BotGameConfig;
import com.xiovr.unibot.bot.BotManager;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.data.dto.WebBotDto;
import com.xiovr.unibot.data.dto.WebEnvDto;
import com.xiovr.unibot.data.service.WebDtoService;
import com.xiovr.unibot.data.service.command.AutoconnectStatus;
import com.xiovr.unibot.data.service.command.WebBotCommand;
import com.xiovr.unibot.data.service.command.WebGlobalCommand;
import com.xiovr.unibot.utils.exceptions.BotDoNotExistsException;
import com.xiovr.unibot.utils.exceptions.BotScriptCannotStopException;

/**
 * @author xio4 Restful controller to exchange with AngularJS (manage section)
 */
@RestController
@RequestMapping(value = { "/v1" })
public class ManageController {
	@Autowired
	WebDtoService webDtoService;
	
	@Autowired
	BotAutoconnection botAutoconnection;

	// Get bots list
	@RequestMapping(value = { "/bot" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<WebBotDto> getBots() {
		return webDtoService.getBots();
	}

	// Delete bot
	@RequestMapping(value = { "/bot/{type}/{id}" }, method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteBot(@PathVariable("type") String type,
			@PathVariable("id") int id) {
		webDtoService.deleteBot(type, id);
	}

	// Update bot
	@RequestMapping(value = { "/bot/{type}/{id}" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void updateBot(@PathVariable("type") String type,
			@PathVariable("id") int id, @RequestBody @Valid WebBotDto botDto) {
		webDtoService.updateBot(type, id, botDto);

	}

	// Create new bot
	@RequestMapping(value = { "/bot" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void newBot(@RequestBody @Valid WebBotDto botDto) {
		webDtoService.addBot(botDto);
	}

	// Autoconnect 
	@RequestMapping(value = { "/bot/autoconnect" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public AutoconnectStatus botChangeAutoconnect(@RequestBody @Valid AutoconnectStatus autoconnect) {
		System.out.println("Autoconnect status=" + autoconnect.status);
		if (autoconnect.status) {
			botAutoconnection.start();
		} else {
			botAutoconnection.stop();
		}
		return autoconnect;
	}

	// Autoconnect 
	@RequestMapping(value = { "/bot/autoconnect" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public AutoconnectStatus botAutoconnect() {
		AutoconnectStatus as = new AutoconnectStatus();
		as.status = botAutoconnection.enabled();
		return as;
	}

	// Bots actions
	@RequestMapping(value = { "/bot/actions" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void botGlobalActions(@RequestBody @Valid WebGlobalCommand command) {
		if ("disconnectAll".equals(command.opcode)) {
			webDtoService.disconnectAllBots();
		}
	}

	// Target bot actions
	@RequestMapping(value = { "/bot/{type}/{id}/actions" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void botActions(@PathVariable("type") String type,
			@PathVariable("id") int id,
			@RequestBody @Valid WebBotCommand command) {
		if ("disconnect".equals(command.opcode)) {
			webDtoService.disconnectBot(type, id);
		} else if ("connect".equals(command.opcode)) {
			webDtoService.connectBot(type, id);
		}
	}

	@RequestMapping(value = { "/env" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public WebEnvDto getEnvironment() {
		return webDtoService.getEnv();
	}

	@RequestMapping(value = { "/env" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void saveEnvironment(@RequestBody @Valid WebEnvDto envDto) {
		webDtoService.saveEnv(envDto);
	}
}
