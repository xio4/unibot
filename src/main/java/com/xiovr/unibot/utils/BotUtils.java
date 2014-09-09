package com.xiovr.unibot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class BotUtils {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	/**
	 * Convert bytes to hex string
	 * 
	 * @param bytes
	 * @return hex string
	 */

	public static String bytesToHex(byte[] bytes, int size, char delim) {
		char[] hexChars;
		if (bytes == null)
			return "";
		if (delim == 0) {
			hexChars = new char[size* 2];
			for (int j = 0; j < size; j++) {
				int v = bytes[j] & 0xFF;
				hexChars[j * 2] = hexArray[v >>> 4];
				hexChars[j * 2 + 1] = hexArray[v & 0x0F];
			}
		} else {
			hexChars = new char[size* 3];
			for (int j = 0; j < size; j++) {
				int v = bytes[j] & 0xFF;
				hexChars[j * 3] = hexArray[v >>> 4];
				hexChars[j * 3 + 1] = hexArray[v & 0x0F];
				hexChars[j * 3 + 2] = delim;
			}
		}
		return new String(hexChars);
	}
	
	public static String bytesToHex(byte[] bytes, char delim) {
		return bytesToHex(bytes, bytes.length, delim);

	}

	public static String longToHex(long val, char delim)
	{
		byte[] bytes = new byte[8];
		for (int i=7; i >= 0; i-=1) {
			bytes[7-i] = (byte)((val >>> (i*8)) & 0xFF);
		}
		return bytesToHex(bytes, delim);
	}
	
	public static String intToHex(int val, char delim)
	{
		byte[] bytes = new byte[4];
		for (int i=3; i >= 0; i-=1) {
			bytes[3-i] = (byte)((val >>> (i*8)) & 0xFF);
		}
		return bytesToHex(bytes, delim);
	}
	public static byte[] hexStringToByteArray(String s) {

        s = s.replaceAll("\\s", "");
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

    /**
     * Load a Properties File
     * @param propsFile
     * @return Properties
     * @throws IOException
     */
    public static Properties loadProperties(File propsFile) throws IOException 
    {
        Properties props = new SortedProperties();
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
    

}
