package com.xiovr.e5bot.bot;

import java.net.InetSocketAddress;

import org.eclipse.jdt.annotation.NonNull;

public interface BotEnvironment {
	public void setUpdateInterval(long updatInterval);
	public long getUpdateInterval();
	public InetSocketAddress getClientAddress();
	public void setClientAddress(@NonNull InetSocketAddress address);
	public InetSocketAddress getServerAddress();
	public void setServerAddress(@NonNull InetSocketAddress address);
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
