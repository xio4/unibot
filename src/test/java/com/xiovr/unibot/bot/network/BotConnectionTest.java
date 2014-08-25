package com.xiovr.unibot.bot.network;

import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.impl.BotContextImpl;
import com.xiovr.unibot.bot.network.BotConnection;
import com.xiovr.unibot.bot.network.impl.BotConnectionServerImpl;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.PacketPool;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.bot.packet.impl.PacketImpl;
import com.xiovr.unibot.bot.packet.impl.RingBufferPacketPoolImpl;
import com.xiovr.unibot.bot.packet.impl.RingBufferPoolImpl;
import com.xiovr.unibot.utils.EchoServer;

public class BotConnectionTest extends TestBase {

	private static final Logger logger = LoggerFactory
			.getLogger(BotConnectionTest.class);

	@Test()
	public void testBotConnection_check_connect_to_echo_server()
			throws InterruptedException {

		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			logger.info("Start echo server");
			System.out.println("Start echo server");
			InetSocketAddress address = new InetSocketAddress("localhost", 8888);
			EchoServer echoServer = new EchoServer(address);
			Thread echoServerThread = new Thread(echoServer);
			echoServerThread.start();
			while (!echoServerThread.isAlive()) {
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
//				Packet pck2 = buffer.poll(PacketPool.obtain());
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
			echoServerThread.interrupt();
			echoServerThread.join();
			// } catch (Exception e) {
			// e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

}
