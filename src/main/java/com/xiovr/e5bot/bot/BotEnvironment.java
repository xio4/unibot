package com.xiovr.e5bot.bot;

import java.net.InetSocketAddress;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public interface BotEnvironment {
	public void setUpdateInterval(long updatInterval);
	public long getUpdateInterval();
	public List<InetSocketAddress> getClientAddresses();
	public void addClientAddress(@NonNull InetSocketAddress address);
	public List<InetSocketAddress> getServerAddresses();
	public void addServerAddress(@NonNull InetSocketAddress address);
	public int getNextBotConnectionInterval();
	public void setNextBotConnectionInterval(int interval);
	public boolean isProxy();
	public void setProxy(boolean bProxy);
	/**
	 * @return true if raw data is set in script and false else
	 */
	public boolean isRawData();
	public void setRawData(boolean bRawData);

	/**
	 * @return true if wait then plugin handled packet and false else
	 */
//	public boolean isWorkPluginWait();
//	public void setWorkPluginWait(boolean bWait);

}
