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
package com.xiovr.unibot.plugin;

public interface PluginLoader {

	public static final String PROPERTY_FILE_NAME = "plugin.properties";
	public static final String CRYPTOR_PARAM_CLASS_NAME = "cryptor.class_name";
	public static final String SCRIPT_PARAM_CLASS_NAME = "script.class_name";
	public void loadCryptorPlugin(String fileName) throws Exception;
	public CryptorPlugin createCryptorPlugin();
	public ScriptPlugin createScriptPlugin(String fileName) throws Exception;
	public void unloadPlugin(Class<?> _class);

}
