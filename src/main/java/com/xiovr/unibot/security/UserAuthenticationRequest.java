package com.xiovr.unibot.security;

public class UserAuthenticationRequest {
	private String name;
	private String login;
	public UserAuthenticationRequest(String name, String login) {
		this.name = name;
		this.login = login;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
}
