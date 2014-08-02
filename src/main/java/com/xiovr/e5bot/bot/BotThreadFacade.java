package com.xiovr.e5bot.bot;

import com.xiovr.e5bot.plugin.ScriptPluginRunnable;

public interface BotThreadFacade {

	public ScriptPluginRunnable getScriptPluginRunnable();
	public void setScriptPluginRunnable(ScriptPluginRunnable spr);
	public Thread getScriptPluginThread();
	public void setScriptPluginThread(Thread thread);

}
