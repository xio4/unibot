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
package com.xiovr.unibot.bot.packet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.xiovr.unibot.TestBase;
import com.xiovr.unibot.bot.packet.Packet;
import com.xiovr.unibot.bot.packet.RingBufferPool;
import com.xiovr.unibot.bot.packet.impl.PacketImpl;
import com.xiovr.unibot.bot.packet.impl.RingBufferPacketPoolImpl;

public class RingBufferPacketPoolTest extends TestBase {

	@Test
	public void ringBufferPacketPool_put_poll_and_combinations()
	{
		RingBufferPool<Packet> rbp = new RingBufferPacketPoolImpl(100);
		Random rnd = new Random(System.currentTimeMillis());
		
		List<Packet> pckList = new ArrayList<>();
		
		for (int k = 0 ; k < 1000; ++k) {
		
		int size = rnd.nextInt(100);
		
		for (int i=0; i < size; ++i) {
			Packet pck = new PacketImpl();
			pck.clear();
			pck.writeC(0x10);
			pck.writeD(rnd.nextInt());
			pck.writeQ(rnd.nextLong());
			pck.writeS("wtfffffeJKKAHOD_+-/*ВТФВав!?");
			pck.writeF(rnd.nextDouble());
			pck.putHeader();
			pckList.add(pck);
			try {
				rbp.put(pck);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i=0; i < size; ++i) {
			Packet pck= null;
			try {
				pck = rbp.poll();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assert.assertTrue(pckList.get(i).equals(pck), "pck not equals in " + 
			i + " pos");
		}
		
		pckList.clear();
		}

	}
}
