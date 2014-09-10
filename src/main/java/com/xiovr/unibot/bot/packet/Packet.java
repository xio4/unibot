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

import java.nio.ByteBuffer;
import com.xiovr.unibot.utils.Pool.Poolable;

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

	public byte[] array();
	
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
	public void writeB(byte[] barray, int start, int len);

	public void writeS(String text);

	public void writeF(double org);

	public void writeC(int value);

	public void writeH(int value);

	public void writeD(int value);
	
	public int getMarkedSize();
	public void markSize();

	public void putHeader(int head);

	public void putHeader();
	public int getHeader();

	public void fill(int offset, int size, byte ch);
	public void putPacketId(int pckId, int size);
}
