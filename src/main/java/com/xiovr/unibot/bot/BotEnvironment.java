package com.xiovr.unibot.bot;

import java.net.InetSocketAddress;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public interface BotEnvironment extends Settings {

	public static final String ENVIRONMENT_CFG_FN = "environment.cfg";

	@Param(name="script.update_interval", values = "100")
	public void setUpdateInterval(long updatInterval);
	public long getUpdateInterval();

	@Param(name="client", values = {"127.0.0.1", "2594", "127.0.0.1", "22273"})
	public void setClientAddresses(@NonNull List<InetSocketAddress> addresses);
	public List<InetSocketAddress> getClientAddresses();
	public void addClientAddress(@NonNull InetSocketAddress address);

	@Param(name="server", values = {"91.206.202.30", "2594", "91.206.202.28", "22273"})
	public void setServerAddresses(@NonNull List<InetSocketAddress> addresses);
	public List<InetSocketAddress> getServerAddresses();
	public void addServerAddress(@NonNull InetSocketAddress address);

	@Param(name="bot.next_connection_interval", values = "10")
	public void setNextBotConnectionInterval(int interval);
	public int getNextBotConnectionInterval();

	@Deprecated
	@Param(name="client.proxy", values = "false")
	public void setProxy(boolean bProxy);
	@Deprecated
	public boolean getProxy();
	/**
	 * @return true if raw data is set in script and false else
	 */
	@Param(name="bot.raw_data", values = "false")
	public void setRawData(boolean bRawData);
	public boolean getRawData();
	
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
