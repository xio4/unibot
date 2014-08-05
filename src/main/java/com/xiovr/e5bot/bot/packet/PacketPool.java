package com.xiovr.e5bot.bot.packet;

import com.xiovr.e5bot.bot.packet.impl.PacketImpl;
import com.xiovr.e5bot.utils.Pool;

public class PacketPool {
	private static final Pool<Packet> pckPool = new Pool<Packet>() {
		@Override
		protected Packet newObject() {
			return new PacketImpl();
		}
	};
	
	public static Packet obtain()
	{
		return pckPool.obtain();
	}
	
	public static void free(Packet pck)
	{
		if (pck != null)
			pckPool.free(pck);
	}

	public static void dispose()
	{
		pckPool.clear();
	}
}
