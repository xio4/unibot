package com.xiovr.e5bot.bot.packet;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReferenceArray;

import com.xiovr.e5bot.utils.Pool.Poolable;

public interface Packet extends Poolable {
	// AtomicReferenceArray ara;
	// CountDownLatch cdl;
	// FutureTask ft;
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
	
	public int getConnStage();
	public void setConnStage(int stage);

	public void clear();

	/**
	 * @return time in milliseconds
	 */
	public long getTime();

	/**
	 * @param time
	 *            in milliseconds
	 */
	public void setTime(long time);

	/**
	 * @return packet number
	 */
	public long getNum();

	/**
	 * @param pckNum
	 *            set packet number
	 */
	public void setNum(long pckNum);

	/**
	 * @param id
	 *            packet owner
	 */
	@Deprecated
	public void setOwnerId(int id);

	/**
	 * @return packet owner
	 */
	@Deprecated
	public int getOwnerId();

	/**
	 * @return packet type
	 */
	public int getType();

	/**
	 * @param type
	 *            packet type
	 */
	public void setType(int type);

	public int getAllocateSize();

	/**
	 * @return inside buffer
	 */
	public ByteBuffer getBuf();

	public int readD();

	public int readC();

	public int readH();

	public double readF();

	public String readS();

	public byte[] readB(int length);

	public long readQ();

	/**
	 * padding to mod8 for checksum
	 */
	public void pad();

	public int getPosition();
	public void setPosition(int pos);

	public void writeQ(long value);

	public void writeB(byte[] barray);

	public void writeS(String text);

	public void writeF(double org);

	public void writeC(int value);

	public void writeH(int value);

	public void writeD(int value);
	
	public int getMarkedSize();
	public void markSize();

	void putHeader(int head);

	void putHeader();
	public int getHeader();
}
