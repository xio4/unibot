package com.xiovr.unibot.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class WebAuthenticationToken implements Authentication {

	private UserAuthenticationResponse userAuthenticationResponse;
	private Set<GrantedAuthority> authorities;
	public WebAuthenticationToken(UserAuthenticationResponse uar) {
		this.userAuthenticationResponse = uar;
		authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(uar.getRole().toString()));
	}

	@Override
	public String getName() {
		return this.userAuthenticationResponse.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return this.userAuthenticationResponse.getRole().toString();
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.userAuthenticationResponse.getLogin();
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {
	}

}
