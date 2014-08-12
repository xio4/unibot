package com.xiovr.e5bot.plugin.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.xiovr.e5bot.plugin.CryptorPlugin;
import com.xiovr.e5bot.plugin.PluginLoader;
import com.xiovr.e5bot.plugin.ScriptPlugin;

public class PluginLoaderImpl implements PluginLoader {

	Class<CryptorPlugin> cpClazz;

	public PluginLoaderImpl() {
		cpClazz = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadCryptorPlugin(String fileName) throws RuntimeException {
		File file = new File(fileName);
		if (file.isFile() && file.getName().endsWith(".jar")) {
			if (cpClazz == null) {
				cpClazz = (Class<CryptorPlugin>) loadClass(file,
						CRYPTOR_PARAM_CLASS_NAME);
				if (cpClazz == null) {
					throw new RuntimeException(
							"Cannot find CryptorPlugin class");
				}
			}
		}
	}

	@Override
	public CryptorPlugin createCryptorPlugin() {
		CryptorPlugin cp = null;
		assert cpClazz != null;
		try {
			cp = cpClazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return cp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScriptPlugin createScriptPlugin(String fileName) throws RuntimeException {
		ScriptPlugin sp = null;
		File file = new File(fileName);
		Class<ScriptPlugin> clazz;

		if (file.isFile() && file.getName().endsWith(".jar")) {
			clazz = (Class<ScriptPlugin>) loadClass(file,
					SCRIPT_PARAM_CLASS_NAME);
			if (clazz == null) {
				throw new RuntimeException("Can't find ScriptPlugin class");
			}
		} else {
			throw new RuntimeException("Can't load script " + fileName);
		}

		try {
			sp = (ScriptPlugin) clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sp;
	}

	private Class<?> loadClass(File jarFile, String paramName) {
		try {
			Properties props = getPluginProps(jarFile);
			if (props == null)
				throw new IllegalArgumentException("No props file in "
						+ jarFile.getName() + " found");

			String pluginClassName = props.getProperty(paramName);
			if (pluginClassName == null || pluginClassName.length() == 0) {
				return null;
			}

			URL jarURL = jarFile.toURI().toURL();
			URLClassLoader classLoader = new URLClassLoader(
					new URL[] { jarURL });
			Class<?> pluginClass = classLoader.loadClass(pluginClassName);
			classLoader.close();
			return pluginClass;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Properties getPluginProps(File file) throws IOException {
		Properties result = null;
		JarFile jar = new JarFile(file);
		Enumeration<JarEntry> entries = jar.entries();

		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (entry.getName().equals(PROPERTY_FILE_NAME)) {
				// That's it! Load props
				InputStream is = null;
				try {
					is = jar.getInputStream(entry);
					result = new Properties();
					result.load(is);
				} finally {
					if (is != null)
						is.close();
				}
			}
		}
		jar.close();
		return result;
	}
}
