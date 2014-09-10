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
package com.xiovr.unibot.bot.packet.impl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.xiovr.unibot.bot.packet.RingBufferPool;

import java.lang.reflect.Array;

import org.eclipse.jdt.annotation.NonNull;

public abstract class RingBufferPoolImpl<T> implements RingBufferPool<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3702219147249611953L;
	private ReentrantLock lock;
	private Condition fullCond;
	private Condition emptyCond;
	private T[] ring;
	@SuppressWarnings("unused")
	private Class<?> clazz;
	private int ringSize;
	private int readMarker;
	private int writeMarker;
	private volatile boolean bFull;

	public RingBufferPoolImpl(Class<?> clazz)
	{
		this(clazz, RingBufferPool.DEFAULT_SIZE);
	}
	@SuppressWarnings("unchecked")
	public RingBufferPoolImpl(Class<?> clazz, int ringSize) {
		lock = new ReentrantLock();
		fullCond = lock.newCondition();
		emptyCond = lock.newCondition();
//		clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
//				.getActualTypeArguments()[0];
		this.clazz = clazz;
		ring = (T[]) Array.newInstance(clazz, ringSize);
//		pool = (T[]) Array.newInstance(clazz, ringSize);
		readMarker = 0;
		writeMarker = 0;
		this.ringSize = ringSize;
		// for putNb
		this.bFull = false;
	}

	@Override
//	public T put(@NonNull T newObj) throws InterruptedException {
	public void put(@NonNull T newObj) throws InterruptedException {
		lock.lockInterruptibly();
//		System.out.println("PUT");
//			System.out.flush();
		try {
			writeMarker = (++writeMarker) % ringSize;

//			System.out.println("read=" + readMarker + " write = " + writeMarker);
//			System.out.flush();
			while (writeMarker == readMarker)
				fullCond.await();
//			T free = ring[writeMarker];
			ring[writeMarker] = newObj;
			emptyCond.signal();
//			return free;
		}
		finally {
			lock.unlock();
		}
	}

	@Override
//	public T poll(@NonNull T freeObj) throws InterruptedException {
	public T poll() throws InterruptedException {
		lock.lockInterruptibly();
//		System.out.println("POLL");
//			System.out.flush();
		try {
			while (writeMarker == readMarker)
				emptyCond.await();
			readMarker = (++readMarker) % ringSize;
//			System.out.println("read=" + readMarker + " write = " + writeMarker);
//			System.out.flush();
			T data = ring[readMarker];
//			ring[readMarker] = freeObj;
			fullCond.signal();
			return data;
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public int size() {
		return ringSize;
	}

	@Override
	public T putNb(@NonNull T newObj) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			if (!bFull) { 
				writeMarker = (++writeMarker) % ringSize;
				if (writeMarker == readMarker)
					bFull = true;
			}
			else {
				if (writeMarker != readMarker) {
					bFull = false;
					writeMarker = (++writeMarker) % ringSize;
				}
				else
				return newObj;
			}
			T oldObj = ring[writeMarker];
			ring[writeMarker] = newObj;
			emptyCond.signal();
			return oldObj;
		}
		finally {
			lock.unlock();
		}
	}
	@Override
	public boolean isNbFull() {
		return bFull;
	}
	@Override
	public int count() throws InterruptedException {
		lock.lockInterruptibly();
//		System.out.println("COUNT write=" + writeMarker + " read=" + readMarker);
//			System.out.flush();
		try {
		int cnt = writeMarker - readMarker;
		if (cnt < 0) {
			cnt = ringSize + cnt;
		}
		return cnt;
		}
		finally {
			lock.unlock();
		}
	}
	


}
