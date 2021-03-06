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
package com.xiovr.unibot.utils;

/*
 * Java NIO echo server example.
 * http://ishbits.googlecode.com/svn-history/r29/trunk/java.nio.EchoServer/EchoServer.java
 */

import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.spi.*;
import java.net.*;
import java.util.*;

/**
 * The client object. This is currently only used to queue the data waiting to
 * be written to the client.
 */
class EchoClient {
	private LinkedList<ByteBuffer> outq;

	EchoClient() {
		outq = new LinkedList<ByteBuffer>();
	}

	// Return the output queue.
	public LinkedList<ByteBuffer> getOutputQueue() {
		return outq;
	}

	// Enqueue a ByteBuffer on the output queue.
	public void enqueue(ByteBuffer bb) {
		outq.addFirst(bb);
	}
}

public class _EchoServer implements Runnable {

	private InetSocketAddress address;
	public _EchoServer(InetSocketAddress address) {
		this.address = address;
	}

	private Selector selector;

	/**
	 * Accept a new client and set it up for reading.
	 */
	private void doAccept(SelectionKey sk) {
		ServerSocketChannel server = (ServerSocketChannel) sk.channel();
		SocketChannel clientChannel;
		try {
			clientChannel = server.accept();
			clientChannel.configureBlocking(false);

			// Register this channel for reading.
			SelectionKey clientKey = clientChannel.register(selector,
					SelectionKey.OP_READ);

			// Allocate an EchoClient instance and attach it to this selection
			// key.
			EchoClient echoClient = new EchoClient();
			clientKey.attach(echoClient);

			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			System.out.println("Accepted connection from "
					+ clientAddress.getHostAddress() + ".");
		} catch (Exception e) {
			System.out.println("Failed to accept new client.");
			e.printStackTrace();
		}

	}

	/**
	 * Read from a client. Enqueue the data on the clients output queue and set
	 * the selector to notify on OP_WRITE.
	 */
	private void doRead(SelectionKey sk) {
		SocketChannel channel = (SocketChannel) sk.channel();
		ByteBuffer bb = ByteBuffer.allocate(8192);
		int len;

		try {
			len = channel.read(bb);
			if (len < 0) {
				disconnect(sk);
				return;
			}
		} catch (Exception e) {
			System.out.println("Failed to read from client.");
			e.printStackTrace();
			return;
		}

		// Flip the buffer.
		bb.flip();

		EchoClient echoClient = (EchoClient) sk.attachment();
		echoClient.enqueue(bb);

		// We've enqueued data to be written to the client, we must
		// not set interest in OP_WRITE.
		sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

	/**
	 * Called when a SelectionKey is ready for writing.
	 */
	private void doWrite(SelectionKey sk) {
		SocketChannel channel = (SocketChannel) sk.channel();
		EchoClient echoClient = (EchoClient) sk.attachment();
		LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

		ByteBuffer bb = outq.getLast();
		try {
			int len = channel.write(bb);
			if (len == -1) {
				disconnect(sk);
				return;
			}

			if (bb.remaining() == 0) {
				// The buffer was completely written, remove it.
				outq.removeLast();
			}
		} catch (Exception e) {
			System.out.println("Failed to write to client.");
			e.printStackTrace();
		}

		// If there is no more data to be written, remove interest in
		// OP_WRITE.
		if (outq.size() == 0) {
			sk.interestOps(SelectionKey.OP_READ);
		}
	}

	private void disconnect(SelectionKey sk) {
		SocketChannel channel = (SocketChannel) sk.channel();

		InetAddress clientAddress = channel.socket().getInetAddress();
		System.out.println(clientAddress.getHostAddress() + " disconnected.");

		try {
			channel.close();
		} catch (Exception e) {
			System.out.println("Failed to close client socket channel.");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Thread curThread = Thread.currentThread();
		try {
			selector = SelectorProvider.provider().openSelector();

			// Create non-blocking server socket.
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);

			// Bind the server socket to localhost.
			ssc.socket().bind(address);

			// Register the socket for select events.
//			SelectionKey acceptKey = ssc.register(selector,
//					SelectionKey.OP_ACCEPT);

			while (!curThread.isInterrupted()){
				selector.select();
				Set<?> readyKeys = selector.selectedKeys();
				Iterator<?> i = readyKeys.iterator();

				while (i.hasNext()) {
					SelectionKey sk = (SelectionKey) i.next();
					i.remove();

					if (sk.isAcceptable()) {
						doAccept(sk);
					}
					if (sk.isValid() && sk.isReadable()) {
						doRead(sk);
					}
					if (sk.isValid() && sk.isWritable()) {
						doWrite(sk);
					}
				}
			}
		} catch (Exception e) {

		}

	}
}