package com.xiovr.e5bot.bot.packet;

import java.util.concurrent.locks.ReentrantLock;

import com.xiovr.e5bot.bot.packet.impl.PacketImpl;
import com.xiovr.e5bot.utils.Pool;

public class PacketPool {
	private static final Pool<Packet> pckPool = new Pool<Packet>() {
		@Override
		protected Packet newObject() {
			return new PacketImpl();
		}
	};
	private static final ReentrantLock lock = new ReentrantLock();
	
	public Pool<Packet> getPool()
	{
		return pckPool;
	}
	
	public static Packet obtain()
	{
		//FIXME Avoid locks
		lock.lock();
		try {
			return pckPool.obtain();
		}
		finally {
			lock.unlock();
		}
	}
	
	public static Packet obtainUnsafe()
	{
        return pckPool.obtain();
	}
	
	public static void freeUnsafe(Packet pck)
	{
		if (pck != null) {
                pckPool.free(pck);
		}
	}

	public static void free(Packet pck)
	{
		if (pck != null) {
			//FIXME Avoid locks
			lock.lock();
			try {
				pckPool.free(pck);
			}
			finally {
				lock.unlock();
			}
		}
	}

	public static void dispose()
	{
		pckPool.clear();
	}
}
