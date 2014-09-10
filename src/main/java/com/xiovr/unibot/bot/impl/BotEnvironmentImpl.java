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

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Component;

import com.xiovr.unibot.bot.BotEnvironment;

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

	public BotEnvironmentImpl() {
		clientAddresses = new ArrayList<InetSocketAddress>();
		serverAddresses = new ArrayList<InetSocketAddress>();
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
	public boolean getProxy() {
		return this.bProxy;
	}

	@Override
	public void setProxy(boolean bProxy) {
		this.bProxy = bProxy;
	}

	@Override
	public boolean getRawData() {
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
		this.portRangeMin = port;
		
	}

	@Override
	public void setPortRangeMax(int port) {
		this.portRangeMax = port;
	}

	@Override
	public void setClientAddresses(@NonNull List<InetSocketAddress> addresses) {
		this.clientAddresses = addresses;
		
	}

	@Override
	public void setServerAddresses(@NonNull List<InetSocketAddress> addresses) {
		this.serverAddresses = addresses;
		
	}


}
