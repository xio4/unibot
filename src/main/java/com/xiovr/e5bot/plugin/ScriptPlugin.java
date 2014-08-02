package com.xiovr.e5bot.plugin;

import com.xiovr.e5bot.bot.BotContext;
import com.xiovr.e5bot.bot.packet.Packet;

/**
 * @author xio4
 * Base interface for java scripts
 */
public interface ScriptPlugin {
	/**
	 * Init script before start
	 * @param context is context to access with bot environment
	 */
	public void init(BotContext context);
	/**
	 * Open method then start server connection
	 */
	public void onOpen();
	/**
	 * @return name script for identify in logs
	 */
	public String getName();
	/**
	 * Update bot logic
	 */
	public void update();
	/**
	 * Income package from server or client
	 * @param deltaTime is time interval between two onPck() invokes
	 */
	public void onPck(Packet pck, float deltaTime);
	/**
	 * Close method then close server connection 
	 */
	public void onClose();
	/**
	 * Release script after stop one or bot
	 */
	public void dispose();
	
	/**
	 * @param senderName Name of sender message
	 * @param msg The message is wrote by other bot
	 */
	public void onReadBotMsg(String senderName, String msg);

}
