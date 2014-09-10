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
package com.xiovr.unibot.bot;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.xiovr.unibot.bot.network.BotConnection;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.ScriptPlugin;

public interface BotContext {
	public static final int OFFLINE_STATUS = 0x00;
	public static final int CONN_STATUS = 0x01;
	public static final int DISCONN_STATUS = 0x03;
	public static final int INWORLD_STATUS = 0x05;
	
	public static final int LOG_INFO_TYPE = 0x01;
	public static final int LOG_WARN_TYPE = 0x02;
	public static final int LOG_ERR_TYPE = 0x03;


	public int getBotId();
	public void setBotId(int botId);
	public int getConnectStage();
	public void setConnectStage(int stage);
	public BotEnvironment getBotEnvironment();
	public void setBotEnvironment(@NonNull BotEnvironment botEnvironment);
	public List<BotConnection> getServerConnections();
	public void addServerConnection(@NonNull BotConnection botConnection);
	public List<BotConnection> getClientConnections();
	public void addClientConnection(@NonNull BotConnection botConnection);
	// TODO Not implemented yet
	public void reconnect(int seconds);
	public boolean setStatus(int status);
	public int getStatus();
	public void sendToServer(Packet pck) throws InterruptedException;
	public void sendToClient(Packet pck) throws InterruptedException;
	public void sendToServerAndFlush(Packet pck) throws InterruptedException;
	public void sendToClientAndFlush(Packet pck) throws InterruptedException;
	public boolean sendMsgToBot(String name, String msg);
	public void setScript(ScriptPlugin script);
	public ScriptPlugin getScript();
	public void setCryptorPlugin(@NonNull CryptorPlugin cryptor);
	public CryptorPlugin getCryptorPlugin();
	@Deprecated
	public RingBufferPool<Packet> getWriteClientBuffer();
	@Deprecated
	public void setWriteClientBuffer(@NonNull RingBufferPool<Packet> sendBuf);
	@Deprecated
	public RingBufferPool<Packet> getWriteServerBuffer();
	@Deprecated
	public void setWriteServerBuffer(@NonNull RingBufferPool<Packet> sendBuf);
	public RingBufferPool<Packet> getReadBuffer();
	public void setReadBuffer(@NonNull RingBufferPool<Packet> sendBuf);
	public void setScriptPluginFacade(@NonNull ScriptPluginFacade botFacade);
	public ScriptPluginFacade getScriptPluginFacade();
	public void setBotSettings(@NonNull BotSettings botSettings);
	public BotSettings getBotSettings();
	public void setBotLogger(BotLogger botLogger);
	public BotLogger getBotLogger();
	public void log(int type, String msg);
	public BotManager getManager();
	public void setManager(@NonNull BotManager botManager);
}
