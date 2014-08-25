package com.xiovr.unibot.utils.exceptions;

public class BotScriptNotFoundException extends Exception {
	public BotScriptNotFoundException(String scriptPath) {
		super("Cannot found script with path="+scriptPath);
	}

}
