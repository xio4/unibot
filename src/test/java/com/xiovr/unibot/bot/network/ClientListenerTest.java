package com.xiovr.unibot.bot.network;

import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.bot.BotContext;
import com.xiovr.unibot.bot.BotEnvironment;
import com.xiovr.unibot.bot.BotSettings;
import com.xiovr.unibot.bot.ScriptPluginFacade;
import com.xiovr.unibot.bot.impl.BotContextImpl;
import com.xiovr.unibot.bot.impl.BotEnvironmentImpl;
import com.xiovr.unibot.bot.impl.BotSettingsImpl;
import com.xiovr.unibot.bot.impl.ScriptPluginFacadeImpl;
import com.xiovr.unibot.bot.network.BotConnection;
import com.xiovr.unibot.bot.network.ConnectionFactory;
import com.xiovr.unibot.bot.network.impl.BotConnectionServerImpl;
import com.xiovr.unibot.bot.network.impl.ConnectionFactoryImpl;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.PacketPool;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.bot.packet.impl.RingBufferPacketPoolImpl;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.PluginLoader;
import com.xiovr.unibot.plugin.ScriptPluginRunnable;
import com.xiovr.unibot.plugin.impl.PluginLoaderImpl;
import com.xiovr.unibot.plugin.impl.ScriptPluginRunnableImpl;
import com.xiovr.unibot.utils.EchoServer;
import com.xiovr.unibot.utils._EchoServer;

public class ClientListenerTest extends TestBase {

	private EchoServer echoServer;
	private BotContext clientEmulatorContext;
	private NioEventLoopGroup clientEmulWorkerGroup;
	private List<BotContext> proxyBots;
	private ConnectionFactory connectFactory;
	private BotEnvironment botEnvironment;
	private static final int ECHO_SERVER_PORT = 8889;

	@BeforeMethod
	public void before() {
		// Init echo server
		logger.info("Start echo server for ClientListenerTest");
		System.out.println("Start echo server for ClientListenerTest");
		InetSocketAddress address = new InetSocketAddress("localhost",
				ECHO_SERVER_PORT);
		echoServer = new EchoServer(address);
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

		// Create client emulator
		clientEmulWorkerGroup = new NioEventLoopGroup();
		clientEmulatorContext = createClientEmulator(clientEmulWorkerGroup);

		// Load fake cryptor plugin
		PluginLoader pluginLoader = new PluginLoaderImpl();
		String dir = getClass().getProtectionDomain().getCodeSource()
				.getLocation().toString().substring(6);
		pluginLoader.loadCryptorPlugin("/" + dir + "cryptor_fake.jar");
		CryptorPlugin cp = pluginLoader.createCryptorPlugin();
		cp.init(null);

		// Create bot environment
		botEnvironment = createBotEnvironment();

		proxyBots = new ArrayList<BotContext>();

		// Create client connection listener
		connectFactory = new ConnectionFactoryImpl();
		connectFactory.init(botEnvironment);
		connectFactory.createProxyListeners(proxyBots);

		for (int i = 0; i < 2; ++i) {
			BotContext bc = createProxyBot(cp);
			proxyBots.add(bc);
			connectFactory.createBotConnectionClient(bc);
			connectFactory.createBotConnectionServer(bc);
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private @NonNull BotContext createProxyBot(CryptorPlugin cp) {
		// Create proxy bot
		BotContext bc = new BotContextImpl();
		bc.setConnectStage(0);
		bc.setBotEnvironment(botEnvironment);
		bc.setCryptorPlugin(cp);

		BotSettings botSettings = createBotSettings();
		bc.setBotSettings(botSettings);

		// Create script for proxy bot
		RingBufferPool<Packet> readBuf = new RingBufferPacketPoolImpl();
		bc.setReadBuffer(readBuf);
		ScriptPluginFacade spf = new ScriptPluginFacadeImpl();
		ScriptPluginRunnable spr = new ScriptPluginRunnableImpl(bc);
		Thread spt = new Thread(spr);
		spf.setScriptPluginRunnable(spr);
		spf.setScriptPluginThread(spt);
		spt.start();
		bc.setScriptPluginFacade(spf);

		return bc;

	}

	private @NonNull BotSettings createBotSettings() {
		BotSettings bs = new BotSettingsImpl();

		bs.setAutoConnect(false);
		bs.setAutoConnectInterval(10);
		bs.setDisabled(false);
		bs.setLogging(false);
		bs.setModifLogging(false);
		bs.setLogin("test");
		bs.setPassword("password");
		bs.setName("char");
		bs.setScriptPath("");
		bs.setServerId(0);
		bs.setType(BotSettings.PROXY_TYPE);

		return bs;
	}

	private @NonNull BotEnvironment createBotEnvironment() {
		BotEnvironment botEnv = new BotEnvironmentImpl();
		botEnv.setPortRangeMin(10000);
		botEnv.setPortRangeMax(11000);
		botEnv.setProxy(true);
		botEnv.setRawData(true);
		botEnv.setUpdateInterval(100);
		botEnv.setNextBotConnectionInterval(10);
		InetSocketAddress isaS = new InetSocketAddress("localhost",
				ECHO_SERVER_PORT);
		List<InetSocketAddress> lisaS = new ArrayList<InetSocketAddress>();
		lisaS.add(isaS);
		botEnv.setServerAddresses(lisaS);
		InetSocketAddress isaC = new InetSocketAddress("localhost", 8000);
		List<InetSocketAddress> lisaC = new ArrayList<InetSocketAddress>();
		lisaC.add(isaC);
		botEnv.setClientAddresses(lisaC);
		return botEnv;

	}

	private BotContext createClientEmulator(@NonNull NioEventLoopGroup group) {
		RingBufferPool<Packet> buffer = new RingBufferPacketPoolImpl();
		BotContext botContext = new BotContextImpl();
		botContext.setReadBuffer(buffer);
		BotConnection botConnection = new BotConnectionServerImpl();
		botConnection.init(group, botContext, 0);
		botContext.addServerConnection(botConnection);
		botContext.setConnectStage(0);
		return botContext;
	}

	@Test()
	public void clientListener_create_listeners_and_send_packet_to_echo_server() {
		try {
			// Thread.sleep(5000);
			// clientEmulatorContext.getServerConnections().get(0).connect(
			// botEnvironment.getClientAddresses().get(0));
			Thread.sleep(100);
			BotConnection botConnection = clientEmulatorContext
					.getServerConnections().get(0);
			RingBufferPool<Packet> buffer = clientEmulatorContext
					.getReadBuffer();

			// Check two times
			for (int i = 0; i < 2; ++i) {
				botConnection.connect(botEnvironment.getClientAddresses()
						.get(0));
				Thread.sleep(100);
				Packet pck = PacketPool.obtain();
				pck.clear();

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
				// Thread.sleep(500);
				// Assert.assertEquals(buffer.count(), 1);
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	@AfterMethod
	public void after() {
		try {
			for (BotContext bc : proxyBots) {
				bc.getScriptPluginFacade().getScriptPluginThread().interrupt();
				bc.getScriptPluginFacade().getScriptPluginThread().join();
			}
			connectFactory.dispose();
			echoServer.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		clientEmulWorkerGroup.shutdownGracefully();
	}

}
