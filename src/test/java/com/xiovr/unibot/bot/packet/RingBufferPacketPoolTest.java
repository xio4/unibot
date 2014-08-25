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
