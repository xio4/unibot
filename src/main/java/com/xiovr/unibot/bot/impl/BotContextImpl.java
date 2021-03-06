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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.BotLogger;
import com.xiovr.unibot.bot.BotManager;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.bot.ScriptPluginFacade;
import com.xiovr.unibot.bot.network.BotConnection;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.PacketPool;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.ScriptPlugin;

public class BotContextImpl implements BotContext {

	Logger logger = LoggerFactory.getLogger(BotContextImpl.class);
	private int botId;
	private AtomicInteger status;
	private BotEnvironment botEnvironment;
	private CryptorPlugin cryptorPlugin;
	private Packet bcPck;
	private RingBufferPool<Packet> readBuf;
	private RingBufferPool<Packet> writeServerBuf;
	private RingBufferPool<Packet> writeClientBuf;
	private List<BotConnection> serverConnections;
	private List<BotConnection> clientConnections;
	private BotSettings botSettings;
	private volatile ScriptPlugin script;
	private ScriptPluginFacade scriptPluginFacade;
	private BotLogger botLogger;
	private volatile int connStage;
	private BotManager botManager;

	public BotContextImpl() {
		super();
		botId = -1;
		status = new AtomicInteger(BotContext.OFFLINE_STATUS);
		serverConnections = new CopyOnWriteArrayList<BotConnection>();
		clientConnections = new CopyOnWriteArrayList<BotConnection>();
	}

	@Override
	public int getBotId() {
		return botId;
	}

	@Override
	public void setBotId(int botId) {
		this.botId = botId;
	}

	@Override
	public BotEnvironment getBotEnvironment() {
		return botEnvironment;
	}

	@Override
	public void setBotEnvironment(@NonNull BotEnvironment botEnvironment) {
		this.botEnvironment = botEnvironment;
	}


	// TODO reconnect not implemented yet
	@Override
	public void reconnect(int seconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setStatus(int status) {
		int oldStatus = this.status.get();
		return this.status.compareAndSet(oldStatus, status);
	}

	@Override
	public int getStatus() {
		return this.status.get();
	}

	@SuppressWarnings("null")
	@Override
	public void sendToServer(Packet pck) throws InterruptedException {
		if (pck == null)
			return;
        long time = System.currentTimeMillis();
        pck.setType(Packet.PCK_TO_SERVER);
        if (botSettings.getLogging())
        	botLogger.pckLog(pck);
        bcPck = PacketPool.obtain(); 
        cryptorPlugin.encryptToServer(pck, bcPck);
        bcPck.setType(Packet.RAW_PCK_TO_SERVER);
        bcPck.setTime(time);
        PacketPool.free(pck);
//        final Packet chPck = writeServerBuf.put(bcPck);
        serverConnections.get(connStage).writeAndFlush(bcPck);
 //       PacketPool.free(chPck);
        	
	}

	@SuppressWarnings("null")
	@Override
	public void sendToClient(Packet pck) throws InterruptedException {
		if (pck == null)
			return;
        long time = System.currentTimeMillis();
        pck.setType(Packet.PCK_TO_CLIENT);
        if (botSettings.getLogging())
        	botLogger.pckLog(pck);
        bcPck = PacketPool.obtain(); 
        cryptorPlugin.encryptToClient(pck, bcPck);
        bcPck.setType(Packet.RAW_PCK_TO_CLIENT);
        bcPck.setTime(time);
        PacketPool.free(pck);
//        final Packet chPck = writeClientBuf.put(bcPck);
        clientConnections.get(connStage).writeAndFlush(bcPck);
 //       PacketPool.free(chPck);
	}

	//TODO sendMsgToBot not implemented yet
	@Override
	public boolean sendMsgToBot(String name, String msg) {
		logger.error("sendMsgToBot not implemented yet");
		return false;
	}

	@Override
	public void setScript(ScriptPlugin script) {
		this.script = script;
	}

	@Override
	public ScriptPlugin getScript() {
		return this.script;
	}

	@Override
	public void setCryptorPlugin(@NonNull CryptorPlugin cryptor) {
		this.cryptorPlugin = cryptor;
	}

	@Override
	public CryptorPlugin getCryptorPlugin() {
		return this.cryptorPlugin;
	}

	@Override
	public RingBufferPool<Packet> getWriteClientBuffer() {
		return this.writeClientBuf;
	}

	@Override
	public void setWriteClientBuffer(@NonNull RingBufferPool<Packet> sendBuf) {
		this.writeClientBuf = sendBuf;

	}

	@Override
	public RingBufferPool<Packet> getWriteServerBuffer() {
		return this.writeServerBuf;
	}

	@Override
	public void setWriteServerBuffer(@NonNull RingBufferPool<Packet> sendBuf) {
		this.writeServerBuf = sendBuf;

	}

	@Override
	public RingBufferPool<Packet> getReadBuffer() {
		return this.readBuf;
	}

	@Override
	public void setReadBuffer(@NonNull RingBufferPool<Packet> readBuf) {
		this.readBuf = readBuf;

	}

	@Override
	public void setScriptPluginFacade(@NonNull ScriptPluginFacade scriptPlugin) {
		this.scriptPluginFacade = scriptPlugin;

	}

	@Override
	public ScriptPluginFacade getScriptPluginFacade() {
		return scriptPluginFacade;
	}

	@Override
	public void setBotSettings(@NonNull BotSettings botSettings) {
		this.botSettings = botSettings;

	}

	@Override
	public BotSettings getBotSettings() {
		return this.botSettings;
	}

	//TODO log not implemented yet
	@Override
	public void log(int type, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBotLogger(BotLogger botLogger) {
		this.botLogger = botLogger;
		
	}

	@Override
	public BotLogger getBotLogger() {
		return botLogger;
	}

	@Override
	public void sendToServerAndFlush(Packet pck) throws InterruptedException {
		sendToServer(pck);
		serverConnections.get(connStage).flush();
		
	}

	@Override
	public void sendToClientAndFlush(Packet pck) throws InterruptedException {
		sendToClient(pck);
		clientConnections.get(connStage).flush();
		
	}

	@Override
	public int getConnectStage() {
		return this.connStage;
	}

	@Override
	public void setConnectStage(int stage) {
		this.connStage = stage;
		
	}

	@Override
	public List<BotConnection> getServerConnections() {
		return this.serverConnections;
	}


	@Override
	public List<BotConnection> getClientConnections() {
		return this.clientConnections;
	}


	@Override
	public void addServerConnection(@NonNull BotConnection botConnection) {
		this.serverConnections.add(botConnection);
		
	}

	@Override
	public void addClientConnection(@NonNull BotConnection botConnection) {
		this.clientConnections.add(botConnection);
	}

	@Override
	public BotManager getManager() {
		return this.botManager;
	}

	@Override
	public void setManager(@NonNull BotManager botManager) {
		this.botManager = botManager;
	}

}
