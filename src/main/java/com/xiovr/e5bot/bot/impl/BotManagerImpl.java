package com.xiovr.e5bot.bot.impl;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.BotEnvironment;
import com.xiovr.e5bot.bot.BotLauncher;
import com.xiovr.e5bot.bot.BotMessageTransferRunnable;
import com.xiovr.e5bot.bot.BotSettings;
import com.xiovr.e5bot.bot.BotManager;
import com.xiovr.e5bot.bot.network.BotConnection;
import com.xiovr.e5bot.bot.network.ConnectionFactory;
import com.xiovr.e5bot.bot.packet.Packet;
import com.xiovr.e5bot.bot.packet.RingBufferPool;
import com.xiovr.e5bot.plugin.PluginLoader;
import com.xiovr.e5bot.plugin.ScriptPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class BotManagerImpl implements BotManager {

	Logger logger = LoggerFactory.getLogger(BotManagerImpl.class);
	@Override
	public BotEnvironment getBotEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBotEnvironment(BotEnvironment botEnvironment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ConnectionFactory getConnectionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnectionContext(@NonNull ConnectionFactory connContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NonNull BotContext createBot() {

		return new BotContextImpl(); 
	}

	@Override
	public void destroyBot(int botId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BotContext getBot(int botId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loadScript(int botId, ScriptPlugin script) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stopScript(int botId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startScript(int botId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeScript(int botId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BotContext> getBots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int botsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void connect(int botId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect(int botId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BotMessageTransferRunnable getBotMessageTransfer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBotMessageTransfer(@NonNull BotMessageTransferRunnable bmtt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPluginLoader(@NonNull PluginLoader pluginLoader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PluginLoader getPluginLoader() {
		// TODO Auto-generated method stub
		return null; 
	}

	@Override
	public RingBufferPool<Packet> getSendBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSendBuffer(RingBufferPool<Packet> sendBuf) {
		// TODO Auto-generated method stub
		
	}
//	AtomicReferenceArray<BotContext> botContexts;


	@Override
	public void loadBots() {
		
	}

}
