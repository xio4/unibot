package com.xiovr.unibot.utils.exceptions;

public class BotScriptNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BotScriptNotFoundException(String scriptPath) {
		super("Cannot found script with path="+scriptPath);
	}

}
