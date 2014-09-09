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
