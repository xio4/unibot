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

import java.util.concurrent.locks.ReentrantLock;

import com.xiovr.unibot.bot.packet.impl.PacketImpl;
import com.xiovr.unibot.utils.Pool;

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
