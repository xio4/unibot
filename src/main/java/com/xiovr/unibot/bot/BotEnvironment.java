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

import java.net.InetSocketAddress;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public interface BotEnvironment extends Settings {

	public static final String ENVIRONMENT_CFG_FN = "environment.cfg";

	@Param(name="script.update_interval", values = "100")
	public void setUpdateInterval(long updatInterval);
	public long getUpdateInterval();

	@Param(name="script.pathprefix", values = "scripts")
	public void setScriptsPathPrefix(String scriptsPath);
	public String getScriptsPathPrefix();

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
	@Param(name="bot.cryptor_path", values = "cryptor.jar")
	public void setCryptorPath(String cryptorPath);
	public String getCryptorPath();


	/**
	 * @return true if wait then plugin handled packet and false else
	 */
//	public boolean isWorkPluginWait();
//	public void setWorkPluginWait(boolean bWait);

}
