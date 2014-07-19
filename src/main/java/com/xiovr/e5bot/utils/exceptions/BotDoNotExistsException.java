package com.xiovr.e5bot.utils.exceptions;

public class BotDoNotExistsException extends Exception
{
	private static final long serialVersionUID = 1L; 
	public BotDoNotExistsException(Integer botId)
	{
		super("Cannot find bot with id="+Integer.toString(botId));
	}
	

}
