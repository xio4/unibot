package com.xiovr.unibot.bot;

import com.xiovr.unibot.plugin.ScriptPluginRunnable;

public interface ScriptPluginFacade {

	public ScriptPluginRunnable getScriptPluginRunnable();
	public void setScriptPluginRunnable(ScriptPluginRunnable spr);
	public Thread getScriptPluginThread();
	public void setScriptPluginThread(Thread thread);

}
