package com.xiovr.unibot.plugin;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.bot.packet.impl.PacketImpl;
import com.xiovr.unibot.plugin.CryptorPlugin;
import com.xiovr.unibot.plugin.PluginLoader;
import com.xiovr.unibot.plugin.impl.PluginLoaderImpl;

public class CryptorPluginTest extends TestBase {
	@Test
	public void cryptorPluginFake_load_and_execute_init_method() {
		PluginLoader pluginLoader = new PluginLoaderImpl();
		String dir = getClass().getProtectionDomain().getCodeSource()
				.getLocation().toString().substring(6);
		try {
			pluginLoader.loadCryptorPlugin("/"+dir+"cryptor_fake.jar");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CryptorPlugin cp = pluginLoader.createCryptorPlugin();
		cp.init(null);
		Assert.assertNotNull(cp.encryptToClient(new PacketImpl(), new PacketImpl()));
		Assert.assertNotNull(cp.encryptToServer(new PacketImpl(), new PacketImpl()));
		Assert.assertNotNull(cp.decryptFromClient(new PacketImpl(), new PacketImpl()));
		Assert.assertNotNull(cp.decryptFromServer(new PacketImpl(), new PacketImpl()));
		
	}

}
