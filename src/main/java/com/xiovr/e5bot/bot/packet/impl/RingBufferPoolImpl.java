package com.xiovr.e5bot.bot.packet.impl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.xiovr.e5bot.bot.packet.RingBufferPool;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;

public class RingBufferPoolImpl<T> implements RingBufferPool<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3702219147249611953L;
	private ReentrantLock lock;
	private Condition fullCond;
	private Condition emptyCond;
	private T[] ring;
	private Class<?> clazz;
	private int ringSize;
	private int readMarker;
	private int writeMarker;

	public RingBufferPoolImpl()
	{
		this(RingBufferPool.DEFAULT_SIZE);
	}
	@SuppressWarnings("unchecked")
	public RingBufferPoolImpl(int ringSize) {
		lock = new ReentrantLock();
		fullCond = lock.newCondition();
		emptyCond = lock.newCondition();
		clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		ring = (T[]) Array.newInstance(clazz, ringSize);
//		pool = (T[]) Array.newInstance(clazz, ringSize);
		readMarker = 0;
		writeMarker = 0;
		this.ringSize = ringSize;
	}

	@Override
	public T put(T newObj) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			writeMarker = (++writeMarker) % ringSize;
			while (writeMarker == readMarker)
				fullCond.await();
			T free = ring[writeMarker];
			ring[writeMarker] = newObj;
			emptyCond.signal();
			return free;
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public T poll(T freeObj) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			while (writeMarker == readMarker)
				emptyCond.await();
			readMarker = (++readMarker) % ringSize;
			T data = ring[readMarker];
			ring[readMarker] = freeObj;
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



}
