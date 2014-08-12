package com.xiovr.e5bot.bot.impl;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

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
	private List<InetSocketAddress> clientAddresses;
	private List<InetSocketAddress> serverAddresses;
	private int portRangeMin;
	private int portRangeMax;
	private int freePort;

	public BotEnvironmentImpl() {
		clientAddresses = new ArrayList<InetSocketAddress>();
		serverAddresses = new ArrayList<InetSocketAddress>();
		freePort = -1;
	}

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
	public List<InetSocketAddress> getClientAddresses() {
		return this.clientAddresses;
	}

	@Override
	public void addClientAddress(@NonNull InetSocketAddress address) {
		this.clientAddresses.add(address);
	}

	@Override
	public List<InetSocketAddress> getServerAddresses() {
		return this.serverAddresses;
	}

	@Override
	public void addServerAddress(@NonNull InetSocketAddress address) {
		this.serverAddresses.add(address);
		
	}

	@Override
	public int getPortRangeMin() {
		return this.portRangeMin;
	}

	@Override
	public int getPortRangeMax() {
		return this.portRangeMax;
	}

	@Override
	public void setPortRangeMin(int port) {
		freePort = port;
		this.portRangeMin = port;
		
	}

	@Override
	public void setPortRangeMax(int port) {
		this.portRangeMax = port;
	}

	@Override
	public int getNextFreePort() {
		if (freePort >= portRangeMax) {
			throw new RuntimeException("Cannot get next free port");
		}
		return this.freePort++;
	}


}
