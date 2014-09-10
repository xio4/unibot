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
