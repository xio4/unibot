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
package com.xiovr.unibot.bot.network;

import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.impl.BotContextImpl;
import com.xiovr.unibot.bot.network.BotConnection;
import com.xiovr.unibot.bot.network.impl.BotConnectionServerImpl;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.PacketPool;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.bot.packet.impl.RingBufferPacketPoolImpl;
import com.xiovr.unibot.utils.EchoServer;

public class BotConnectionTest extends TestBase {

	private static final Logger logger = LoggerFactory
			.getLogger(BotConnectionTest.class);

	@SuppressWarnings("null")
	@Test()
	public void testBotConnection_check_connect_to_echo_server()
			throws InterruptedException {

		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			logger.info("Start echo server for BotConnection test");
			System.out.println("Start echo server for BotConnection test");
			InetSocketAddress address = new InetSocketAddress("localhost", 8888);
			EchoServer echoServer = new EchoServer(address);
			echoServer.startServer();

			try {
				while (!echoServer.getStarted()) {
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Echo server is started");
			System.out.println("Echo server is started");

			RingBufferPool<Packet> buffer = new RingBufferPacketPoolImpl();
			BotContext botContext = new BotContextImpl();
			botContext.setReadBuffer(buffer);
			BotConnection botConnection = new BotConnectionServerImpl();
			botConnection.init(workerGroup, botContext, 0);
			botContext.addServerConnection(botConnection);

			// Check two times
			for (int i = 0; i < 2; ++i) {
				botConnection.connect(address);
				Thread.sleep(100);
				Packet pck = PacketPool.obtain();

				// pck.putUtf16String("Hello!Привет!");
				pck.writeS("Hello!Привет!");
				pck.writeD(1000);
				pck.writeC(0x10);
				pck.writeD(0x11000);
				pck.writeF(092032.09);
				pck.writeQ(023423L);
				pck.writeB(new byte[] { 10, 10, 10 });
				pck.putHeader();
				botConnection.write(pck);
				botConnection.flush();
				Thread.sleep(100);
				Assert.assertEquals(buffer.count(), 1);
				// Packet pck2 = buffer.poll(PacketPool.obtain());
				Packet pck2 = buffer.poll();

				pck = PacketPool.obtain();
				pck.clear();
				pck.writeS("Hello!Привет!");
				pck.writeD(1000);
				pck.writeC(0x10);
				pck.writeD(0x11000);
				pck.writeF(092032.09);
				pck.writeQ(023423L);
				pck.writeB(new byte[] { 10, 10, 10 });
				pck.putHeader();

				pck.clear();
				pck2.clear();

				Assert.assertEquals(pck2.getHeader(), pck.getHeader());
				Assert.assertEquals(pck2.readS(), pck.readS());
				Assert.assertEquals(pck2.readD(), pck.readD());
				Assert.assertEquals(pck2.readC(), pck.readC());
				Assert.assertEquals(pck2.readD(), pck.readD());
				Assert.assertEquals(pck2.readF(), pck.readF());
				Assert.assertEquals(pck2.readQ(), pck.readQ());

				botConnection.disconnect();
				Thread.sleep(100);
			}
			echoServer.close();
			// } catch (Exception e) {
			// e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

}
