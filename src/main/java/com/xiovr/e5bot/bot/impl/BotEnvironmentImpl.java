package com.xiovr.e5bot.bot.impl;

import java.net.InetSocketAddress;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Component;

import com.xiovr.e5bot.bot.BotEnvironment;

/**
 * @author xio4
 * This class is used to get bot settings, scripts paths
 */
@Component
public class BotEnvironmentImpl implements BotEnvironment {

	private long updateInterval;
	private int nextBotConnectionInterval;
	private boolean bProxy;
	private boolean bRawData;
	private InetSocketAddress clientAddress;
	private InetSocketAddress serverAddress;
	@Override
	public void setUpdateInterval(long updateInterval) {
		this.updateInterval = updateInterval;
		
	}

	@Override
	public long getUpdateInterval() {
		return this.updateInterval;
	}


	@Override
	public int getNextBotConnectionInterval() {
		return this.nextBotConnectionInterval;
	}

	@Override
	public void setNextBotConnectionInterval(int interval) {
		this.nextBotConnectionInterval = interval;
		
	}

	@Override
	public boolean isProxy() {
		return this.bProxy;
	}

	@Override
	public void setProxy(boolean bProxy) {
		this.bProxy = bProxy;
	}

	@Override
	public boolean isRawData() {
		return bRawData;
	}

	@Override
	public void setRawData(boolean bRawData) {
		this.bRawData = bRawData;
	}

//	@Override
//	public boolean isWorkPluginWait() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void setWorkPluginWait(boolean bWait) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public InetSocketAddress getClientAddress() {
		return this.clientAddress;
	}

	@Override
	public void setClientAddress(@NonNull InetSocketAddress address) {
		this.clientAddress = address;
		
	}

	@Override
	public InetSocketAddress getServerAddress() {
		return this.serverAddress;
	}

	@Override
	public void setServerAddress(@NonNull InetSocketAddress address) {
		this.serverAddress = address;
	}


}
