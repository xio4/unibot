package com.xiovr.e5bot.bot.packet;

import java.nio.ByteBuffer;

public interface Packet {
	public static final int PCK_SIZE = 65536;
	public static final int PCK_UNKNOWN = 0x00;
	public static final int PCK_FROM_CLIENT = 0x01;
	public static final int PCK_FROM_SERVER = 0x02;
	public static final int PCK_TO_CLIENT = 0x03;
	public static final int PCK_TO_SERVER = 0x04;
	public void setOwnerId(int id);
	public int getOwnerId();
	public int getType();
	public void setType(int type);
	public int getAllocateSize();
	public ByteBuffer getBuf();
	String toHexString(String delimeter);
}
