package com.xiovr.e5bot.bot.packet.impl;

import com.xiovr.e5bot.bot.packet.Packet;

public class RingBufferPacketPoolImpl extends RingBufferPoolImpl<Packet> {

	public RingBufferPacketPoolImpl() {
		super(Packet.class);
		// TODO Auto-generated constructor stub
	}

	public RingBufferPacketPoolImpl(int ringSize) {
		super(Packet.class, ringSize);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
