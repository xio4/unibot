package com.xiovr.e5bot.utils;

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
}
