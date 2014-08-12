package com.xiovr.e5bot.plugin;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.e5bot.TestBase;
import com.xiovr.e5bot.bot.packet.impl.PacketImpl;
import com.xiovr.e5bot.plugin.impl.PluginLoaderImpl;

public class CryptorPluginTest extends TestBase {
	@Test
	public void cryptorPluginFake_load_and_execute_init_method() {
		PluginLoader pluginLoader = new PluginLoaderImpl();
		String dir = getClass().getProtectionDomain().getCodeSource()
				.getLocation().toString().substring(6);
		pluginLoader.loadCryptorPlugin("/"+dir+"cryptor_fake.jar");
		CryptorPlugin cp = pluginLoader.createCryptorPlugin();
		cp.init(null);
		Assert.assertNotNull(cp.encryptToClient(new PacketImpl(), new PacketImpl()));
		Assert.assertNotNull(cp.encryptToServer(new PacketImpl(), new PacketImpl()));
		Assert.assertNotNull(cp.decryptFromClient(new PacketImpl(), new PacketImpl()));
		Assert.assertNotNull(cp.decryptFromServer(new PacketImpl(), new PacketImpl()));
		
	}

}
