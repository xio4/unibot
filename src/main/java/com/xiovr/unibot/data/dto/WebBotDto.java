package com.xiovr.unibot.data.dto;

import java.io.Serializable;

public class WebBotDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int botId;
	public String name;
	public String login;
	public String password;
	public String type;
	public int charId;
	public String status;
	public String scriptPath;
	public boolean disabled;
	public boolean autoconnect;
	public int autoconnect_interval;
	public int serverId;
	public boolean logging;
	public boolean modif_logging;

}
