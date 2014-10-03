package com.xiovr.unibot.data.dto;

import java.io.Serializable;

public class WebEnvDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String cryptor_path;
	public int port_range_min;
	public int port_range_max;
	public int update_interval;
	public String pathprefix;
	public int next_connection_interval;
	public String client;
	public String server;
	public boolean raw_data;
}
