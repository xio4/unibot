package com.xiovr.unibot.utils.exceptions;

public class BotDoNotExistsException extends Exception
{
	private static final long serialVersionUID = 1L; 
	public BotDoNotExistsException(Integer botId, Integer botType)
	{
		super("Cannot find bot with id="+Integer.toString(botId) + " and type=" +
				Integer.toString(botType));
	}
	

}
