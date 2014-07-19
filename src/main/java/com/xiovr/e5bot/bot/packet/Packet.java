package com.xiovr.e5bot.bot.packet;

public interface Packet {
	public static final int PCK_UNKNOWN = 0x00;
	public static final int PCK_FROM_CLIENT = 0x01;
	public static final int PCK_FROM_SERVER = 0x02;
	public static final int PCK_TO_CLIENT = 0x03;
	public static final int PCK_TO_SERVER = 0x04;
	public int getType();
	public void setType(int type);
}
