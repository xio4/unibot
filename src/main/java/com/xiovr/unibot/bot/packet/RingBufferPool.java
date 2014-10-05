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

import java.io.Serializable;

import org.eclipse.jdt.annotation.NonNull;


/** 
 * RingBuffer uses {@link ReentrantLock} and is blocked on {@link put} and 
 * {@link poll} methods according to ring size
 * @author xio4
 *
 * @param <T>
 */
/**
 * @author luser
 *
 * @param <T>
 */
/**
 * @author luser
 *
 * @param <T>
 */
public interface RingBufferPool<T> extends Serializable {

	public static final int DEFAULT_SIZE = 10000;
	/**
	 * Put element in ring buffer. If return is null then need create new
	 * element for use
	 * @param t
	 * @return new T to use from cyclic buffer
	 * @throws InterruptedException 
	 */
//	public T put(@NonNull T t) throws InterruptedException;
	public void put(@NonNull T t) throws InterruptedException;

	/**
	 * Non block {@link put}
	 * @param t
	 * @return true if item is put and false else 
	 * @throws InterruptedException 
	 */
	public T putNb(@NonNull T newObj) throws InterruptedException;
	/**
	 * @return ring buffer size
	 */
	public int size();
	/**
	 * Poll element from ring buffer and set new element in pool
	 * @param freeObj
	 * @return head element and remove in ring buffer
	 * @throws InterruptedException 
	 */
//	public T poll(@NonNull T freeObj) throws InterruptedException;
	public T poll() throws InterruptedException;

	/**
	 * @return true if full by {@link putNb} and false else
	 */
	public boolean isNbFull();
	
	/**
	 * @return count items in buffer
	 */
	public int count() throws InterruptedException;


	/**
	 * Clear buffer
	 */
	public void clear();
}
