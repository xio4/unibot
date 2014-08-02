package com.xiovr.e5bot.bot.packet;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReferenceArray;

public interface Packet {
	//AtomicReferenceArray ara;
	//CountDownLatch cdl;
	//FutureTask ft;
	public static final int PCK_SIZE = 65536;
	public static final int PCK_UNKNOWN = 0x00;

	public static final int PCK_FROM_CLIENT = 0x01;
	public static final int PCK_FROM_SERVER = 0x02;
	public static final int PCK_TO_CLIENT = 0x03;
	public static final int PCK_TO_SERVER = 0x04;

	public static final int RAW_PCK_FROM_CLIENT = 0x05;
	public static final int RAW_PCK_FROM_SERVER = 0x06;
	public static final int RAW_PCK_TO_CLIENT = 0x07;
	public static final int RAW_PCK_TO_SERVER = 0x08;
	public void clear();
	/**
	 * @return time in milliseconds
	 */
	public long getTime();
	/**
	 * @param time in milliseconds
	 */
	public void setTime(long time);
	/**
	 * @return packet number
	 */
	public long getNum();
	/**
	 * @param pckNum set packet number
	 */
	public void setNum(long pckNum);
	/**
	 * @param id packet owner
	 */
	public void setOwnerId(int id);
	/**
	 * @return packet owner
	 */
	public int getOwnerId();
	/**
	 * @return packet type 
	 */
	public int getType();
	/**
	 * @param type packet type
	 */
	public void setType(int type);
	public int getAllocateSize();
	/**
	 * @return inside buffer
	 */
	public ByteBuffer getBuf();
}
