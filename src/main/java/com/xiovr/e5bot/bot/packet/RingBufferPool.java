package com.xiovr.e5bot.bot.packet;

import java.io.Serializable;

import org.eclipse.jdt.annotation.NonNull;


/** 
 * RingBuffer uses {@link ReentrantLock} and is blocked on {@link put} and 
 * {@link poll} methods according to ring size
 * @author xio4
 *
 * @param <T>
 */
public interface RingBufferPool<T> extends Serializable {

	public static final int DEFAULT_SIZE = 10;
	/**
	 * Put element in ring buffer. If return is null then need create new
	 * element for use
	 * @param t
	 * @return new T to use from cyclic buffer
	 * @throws InterruptedException 
	 */
	public T put(@NonNull T t) throws InterruptedException;

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
	public T poll(@NonNull T freeObj) throws InterruptedException;

	/**
	 * @return true if full by {@link putNb} and false else
	 */
	public boolean isNbFull();
	
	/**
	 * @return count items in buffer
	 */
	public int count() throws InterruptedException;
}
