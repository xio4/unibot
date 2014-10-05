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
package com.xiovr.unibot.plugin;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.packet.Packet;

/**
 * @author xio4
 * Base interface for java scripts
 */
public interface ScriptPlugin {

	/**
	 * Max time in milliseconds which can script methods works
	 */
	public static final int MAX_WORK_TIME = 10;

	public static final int NO_CONNECTION = 0x00;
	public static final int CONN_TO_SERVER = 0x01;
	public static final int CONN_TO_CLIENT = 0x02;
	public static final int DISCONN_FROM_SERVER = 0x03;
	public static final int DISCONN_FROM_CLIENT = 0x04;
	/**
	 * Init script before start
	 * @param context is context to access with bot environment
	 */
	public void init(BotContext context);
	/**
	 * Open method then start server connection
	 */
	public void onConnected(int type);
	/**
	 * @return name script for identify in logs
	 */
	public String getName();
	/**
	 * Update bot logic
	 */
	public void update();
	/**
	 * Income package from server or client
	 * @param deltaTime is time interval between two onPck() invokes
	 */
	public void onPck(Packet pck, float deltaTime);
	/**
	 * Close method then close server connection 
	 */
	public void onDisconnected(int type);
	/**
	 * Release script after stop one or bot
	 */
	public void dispose();
	
	/**
	 * @param senderName Name of sender message
	 * @param msg The message is wrote by other bot
	 */
	public void onReadBotMsg(String senderName, String msg);

}
