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
