package com.xiovr.unibot.security;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class UserAuthenticationResponse implements Serializable {

	public UserAuthenticationResponse() {
		errors = new LinkedHashSet<String>();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String login;
	private Set<String> errors;
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
	public UserRoles getRole() {
		return role;
	}
	public void setRole(UserRoles role) {
		this.role = role;
	}
	private UserRoles role;

	public void addError(String error) {
		if (error != null && !error.trim().equals("") ) {
			this.errors.add(error);
		}
	}
	public boolean isSuccess() {
		return this.errors.isEmpty();
	}
}
