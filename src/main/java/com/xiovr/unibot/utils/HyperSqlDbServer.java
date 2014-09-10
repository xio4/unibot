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
package com.xiovr.unibot.utils;

import java.io.IOException;
import java.util.Properties;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl.AclFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
public class HyperSqlDbServer implements SmartLifecycle {
	private final Logger logger = LoggerFactory
			.getLogger(HyperSqlDbServer.class);
	private HsqlProperties properties;
	private Server server;
	private boolean running = false;

	public HyperSqlDbServer(Properties props) {
		properties = new HsqlProperties(props);
	}

	@Override
	public boolean isRunning() {
		if (server != null)
			server.checkRunning(running);
		return running;
	}

	@Override
	public void start() {
		if (server == null) {
			logger.info("Starting HSQL server...");
			server = new Server();
			try {
				server.setProperties(properties);
				server.start();
				running = true;
			} catch (AclFormatException afe) {
				logger.error("Error starting HSQL server.", afe);
			} catch (IOException e) {
				logger.error("Error starting HSQL server.", e);
			}
		}
	}

	@Override
	public void stop() {
		logger.info("Stopping HSQL server...");
		if (server != null) {
			server.stop();
			running = false;
		}
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable runnable) {
		stop();
		runnable.run();
	}
}