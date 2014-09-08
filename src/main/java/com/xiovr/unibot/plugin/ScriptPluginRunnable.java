package com.xiovr.unibot.plugin;

import org.eclipse.jdt.annotation.NonNull;

public interface ScriptPluginRunnable extends Runnable {
	public void setScript(@NonNull ScriptPlugin script);
}
