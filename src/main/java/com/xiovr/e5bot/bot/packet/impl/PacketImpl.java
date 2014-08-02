package com.xiovr.e5bot.bot.packet.impl;

import java.nio.ByteBuffer;

import com.xiovr.e5bot.bot.packet.Packet;

public class PacketImpl implements Packet {

	private ByteBuffer buf;
	private int bufAllocateSize;
	private int type;
	private int ownerId;
	private long time;
	private long num;

	public PacketImpl()
	{
		this(Packet.PCK_SIZE);

	}
	public PacketImpl(int size) {
		buf = ByteBuffer.allocate(size);
		bufAllocateSize = size;
		type = Packet.PCK_UNKNOWN;
		ownerId = -1;
	}

	@Override
	public int getType() {
		return type;
	}


	@Override
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void setOwnerId(int id) {
		// TODO Auto-generated method stub
		this.ownerId = id;
		
	}

	@Override
	public int getOwnerId() {
		return ownerId;
	}
	@Override
	public int getAllocateSize() {
		return bufAllocateSize;
	}
	@Override
	public ByteBuffer getBuf() {
		return buf;
	}
	@Override
	public long getTime() {
		return this.time;
	}
	@Override
	public void setTime(long time) {
		this.time = time;
		
	}
	@Override
	public long getNum() {
		return num;
	}
	@Override
	public void setNum(long pckNum) {
		this.num = pckNum;
		
	}
	@Override
	public void clear() {
		buf.limit(0);
	}

}
