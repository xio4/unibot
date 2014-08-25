package com.xiovr.unibot.bot.impl;

import com.xiovr.unibot.bot.ScriptPluginFacade;
import com.xiovr.unibot.plugin.ScriptPluginRunnable;

public class ScriptPluginFacadeImpl implements ScriptPluginFacade {
	private ScriptPluginRunnable scriptPluginRunnable;
	private Thread scriptPluginThread;

	@Override
	public ScriptPluginRunnable getScriptPluginRunnable() {
		return this.scriptPluginRunnable;
	}

	@Override
	public void setScriptPluginRunnable(ScriptPluginRunnable spr) {
		this.scriptPluginRunnable = spr;
	}

	@Override
	public Thread getScriptPluginThread() {
		return this.scriptPluginThread;
	}

	@Override
	public void setScriptPluginThread(Thread thread) {
		this.scriptPluginThread = thread;
	}

}