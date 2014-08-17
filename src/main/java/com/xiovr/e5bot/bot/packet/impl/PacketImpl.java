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
	private int offset;
	private byte[] array;
	private int markSize;
	private int stage;

	public PacketImpl() {
		this(Packet.PCK_SIZE);

	}

	public PacketImpl(int size) {
		buf = ByteBuffer.allocate(size);
		array = buf.array();
		bufAllocateSize = size;
		offset = 2;
		type = Packet.PCK_UNKNOWN;
		ownerId = -1;
		markSize = 0;
	}
	@Override
	public void putHeader(int head)
	{
		array[0] = (byte) ((head << 8) & 0xFF);
		array[1] = (byte) (head & 0xFF);
	}
	@Override
	public void putHeader()
	{
		int size = offset;
		array[0] = (byte) ((size << 8) & 0xFF);	
		array[1] = (byte) (size & 0xFF);
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
		// pos 0 and 1 for header
		offset = 2;
	}

	@Override
	public void reset() {
		offset = 2;
		buf.clear();
	}

	@Override
	public int readD() {
		if (offset+4 > bufAllocateSize)
			return 0;
		int result = array[offset++] & 0xff;
		result |= (array[offset++] << 8) & 0xff00;
		result |= (array[offset++] << 0x10) & 0xff0000;
		result |= (array[offset++] << 0x18) & 0xff000000;
		return result;
	}

	@Override
	public int readC() {
		if (offset+1 > bufAllocateSize)
			return 0;
		int result = array[offset++] & 0xff;
		return result;
	}

	@Override
	public int readH() {
		if (offset+2 > bufAllocateSize)
			return 0;
		int result = array[offset++] & 0xff;
		result |= (array[offset++] << 8) & 0xff00;
		return result;
	}

	@Override
	public double readF() {
		if (offset+8 > bufAllocateSize)
			return 0;
		long result = array[offset++] & 0xff;
		result |= (array[offset++] & 0xffL) << 8L;
		result |= (array[offset++] & 0xffL) << 16L;
		result |= (array[offset++] & 0xffL) << 24L;
		result |= (array[offset++] & 0xffL) << 32L;
		result |= (array[offset++] & 0xffL) << 40L;
		result |= (array[offset++] & 0xffL) << 48L;
		result |= (array[offset++] & 0xffL) << 56L;
		return Double.longBitsToDouble(result);
	}

	@Override
	public String readS() {
		String str = new String();
		try {
//			result = new String(array, offset, array.length - offset,
//					"UTF-16LE");
//			result = result.substring(0, result.indexOf(0x00));
//			offset += (result.length() * 2) + 2;
		int ch = readH();
		while (ch != 0x0000 && bufAllocateSize > offset) {
			str += (char)ch;
			ch = readH();
		}
		offset += str.length()*2+2;
		} catch (Exception e) {
		}

		return str;
	}

	@Override
	public final byte[] readB(int length) {
		if (offset+length > bufAllocateSize)
			return null;
		byte[] result = new byte[length];
		System.arraycopy(array, offset, result, 0, length);
		offset += length;
		return result;
	}

	@Override
	public long readQ() {
		if (offset+8 > bufAllocateSize)
			return 0;
		long result = array[offset++] & 0xff;
		result |= (array[offset++] & 0xffL) << 8L;
		result |= (array[offset++] & 0xffL) << 16L;
		result |= (array[offset++] & 0xffL) << 24L;
		result |= (array[offset++] & 0xffL) << 32L;
		result |= (array[offset++] & 0xffL) << 40L;
		result |= (array[offset++] & 0xffL) << 48L;
		result |= (array[offset++] & 0xffL) << 56L;
		return result;
	}

	@Override
	public void writeD(int value) {
		if (offset+4 > bufAllocateSize)
			return;
		array[offset++] = (byte) (value & 0xff);
		array[offset++] = (byte) ((value >> 8) & 0xff);
		array[offset++] = (byte) ((value >> 16) & 0xff);
		array[offset++] = (byte) ((value >> 24) & 0xff);
	}

	@Override
	public void writeH(int value) {
		if (offset+2 > bufAllocateSize)
			return;
		array[offset++] = (byte) (value & 0xff);
		array[offset++] = (byte) ((value >> 8) & 0xff);
	}

	@Override
	public void writeC(int value) {
		if (offset+1 > bufAllocateSize)
			return;
		array[offset++] = (byte) (value & 0xff);
	}

	@Override
	public void writeF(double org) {
		if (offset+8 > bufAllocateSize)
			return;
		long value = Double.doubleToRawLongBits(org);
		array[offset++] = (byte) (value & 0xff);
		array[offset++] = (byte) ((value >> 8) & 0xff);
		array[offset++] = (byte) ((value >> 16) & 0xff);
		array[offset++] = (byte) ((value >> 24) & 0xff);
		array[offset++] = (byte) ((value >> 32) & 0xff);
		array[offset++] = (byte) ((value >> 40) & 0xff);
		array[offset++] = (byte) ((value >> 48) & 0xff);
		array[offset++] = (byte) ((value >> 56) & 0xff);
	}

	@Override
	public void writeS(String text) {
		try {
			if (text != null) {
				final byte[] bytes = text.getBytes("UTF-16LE");
				if (bufAllocateSize > bytes.length + offset) {
					System.arraycopy(bytes, 0, array, offset, bytes.length);
					offset += bytes.length;
				} else
					return;
			}
		} catch (Exception e) {

		}
		array[offset++] = 0;
		array[offset++] = 0;
	}

	@Override
	public void writeB(byte[] barray) {
		try {
			if (array.length > barray.length + offset ) {
				System.arraycopy(barray, 0, array, offset, barray.length);
				offset += barray.length;
			} else
				return;
		} catch (Exception e) {
		}
	}

	@Override
	public void writeQ(long value) {
		if (offset+8 > bufAllocateSize)
			return;
		array[offset++] = (byte) (value & 0xff);
		array[offset++] = (byte) ((value >> 8) & 0xff);
		array[offset++] = (byte) ((value >> 16) & 0xff);
		array[offset++] = (byte) ((value >> 24) & 0xff);
		array[offset++] = (byte) ((value >> 32) & 0xff);
		array[offset++] = (byte) ((value >> 40) & 0xff);
		array[offset++] = (byte) ((value >> 48) & 0xff);
		array[offset++] = (byte) ((value >> 56) & 0xff);
	}

	@Override
	public int getPosition() {
		return offset;
	}

	@Override
	public void pad() {
		// if (this instanceof Init)
		// writeD(0x00); //reserve for XOR initial key

		if (offset+4 > bufAllocateSize) {
			return;
		}
		writeD(0x00); // reserve for checksum
		offset+=4;

		int padding = offset % 8;
		if (padding != 0 && 8-padding+offset < bufAllocateSize) {
			for (int i = padding; i < 8; i++) {
				offset++;
				writeC(0x00);
			}
		}
	}

	@Override
	public int getMarkedSize() {
		return markSize;
	}

	@Override
	public void markSize() {
		markSize = offset;
		
	}

	@Override
	public void setPosition(int pos) {
		if (pos < bufAllocateSize) 
			offset = pos;
	}

	@Override
	public int getHeader() {
		int header = array[1] & 0xff;
		header |= (array[0] << 8) & 0xff00;
		return header;
	}

	@Override
	public int getConnStage() {
		return this.stage;
	}

	@Override
	public void setConnStage(int stage) {
		this.stage = stage;
	}
}
