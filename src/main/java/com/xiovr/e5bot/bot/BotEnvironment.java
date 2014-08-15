package com.xiovr.e5bot.bot;

import java.net.InetSocketAddress;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public interface BotEnvironment {
	@Param(name="script.update_interval", values = "200")
	public void setUpdateInterval(long updatInterval);
	public long getUpdateInterval();

	@Param(name="client", values = {"127.0.0.1", "3535", "127.0.0.1", "3635"})
	public void setClientAddresses(@NonNull List<InetSocketAddress> addresses);
	public List<InetSocketAddress> getClientAddresses();
	public void addClientAddress(@NonNull InetSocketAddress address);

	@Param(name="server", values = {"127.0.0.1", "3535", "127.0.0.1", "3635"})
	public void setServerAddresses(@NonNull List<InetSocketAddress> addresses);
	public List<InetSocketAddress> getServerAddresses();
	public void addServerAddress(@NonNull InetSocketAddress address);

	@Param(name="bot.next_connection_interval", values = { "10" })
	public void setNextBotConnectionInterval(int interval);
	public int getNextBotConnectionInterval();

	@Param(name="client.proxy", values = { "false" })
	public void setProxy(boolean bProxy);
	public boolean isProxy();
	/**
	 * @return true if raw data is set in script and false else
	 */
	@Param(name="bot.raw_data", values = { "false" } )
	public void setRawData(boolean bRawData);
	public boolean isRawData();
	
	@Param(name="bot.port_range_min", values = "25000")
	public void setPortRangeMin(int port);
	public int getPortRangeMin();
	@Param(name="bot.port_range_max", values = "25500")
	public void setPortRangeMax(int port);
	public int getPortRangeMax();


	/**
	 * @return true if wait then plugin handled packet and false else
	 */
//	public boolean isWorkPluginWait();
//	public void setWorkPluginWait(boolean bWait);

}
