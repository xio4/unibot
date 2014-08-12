package com.xiovr.e5bot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class BotUtils {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	/**
	 * Convert bytes to hex string
	 * 
	 * @param bytes
	 * @return hex string
	 */
	public static String bytesToHex(byte[] bytes, char delim) {
		char[] hexChars;
		if (delim == 0) {
			hexChars = new char[bytes.length * 2];
			for (int j = 0; j < bytes.length; j++) {
				int v = bytes[j] & 0xFF;
				hexChars[j * 3] = hexArray[v >>> 4];
				hexChars[j * 3 + 1] = hexArray[v & 0x0F];
			}
		} else {
			hexChars = new char[bytes.length * 3];
			for (int j = 0; j < bytes.length; j++) {
				int v = bytes[j] & 0xFF;
				hexChars[j * 3] = hexArray[v >>> 4];
				hexChars[j * 3 + 1] = hexArray[v & 0x0F];
				hexChars[j * 3 + 2] = delim;
			}
		}
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 3] = hexArray[v >>> 4];
			hexChars[j * 3 + 1] = hexArray[v & 0x0F];
			hexChars[j * 3 + 2] = delim;
		}
		return new String(hexChars);
	}
	
    /**
     * Load a Properties File
     * @param propsFile
     * @return Properties
     * @throws IOException
     */
    public static Properties loadProperties(File propsFile) throws IOException 
    {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(propsFile);
        props.load(fis);    
        fis.close();
        return props;
    }
    
    
    public static void saveProperties(File propsFile, Properties props, String comments) throws IOException
    {
    	FileOutputStream fos = new FileOutputStream(propsFile);
    	props.store(fos, comments);
    	fos.close();
    }
    
	public static Properties getPluginProps(File file) throws IOException {
		Properties result = null;
		JarFile jar = new JarFile(file);
		Enumeration<JarEntry> entries = jar.entries();

		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (entry.getName().equals("plugin.properties")) {
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
