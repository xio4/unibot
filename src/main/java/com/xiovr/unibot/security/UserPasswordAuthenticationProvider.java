package com.xiovr.unibot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.xiovr.unibot.data.service.UserService;

public class UserPasswordAuthenticationProvider implements
		AuthenticationProvider {
	@Autowired
	UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UserAuthenticationResponse authenticationResponse = userService.authenticate(new UserAuthenticationRequest(authentication.getName(), authentication.getCredentials().toString()));
		if (authenticationResponse.isSuccess()) {
			SecurityContextHolder.getContext().setAuthentication(new WebAuthenticationToken(authenticationResponse));;
			return SecurityContextHolder.getContext().getAuthentication();
		}
		throw new AuthenticationServiceException(String.format("The username [%s] could not be authenticated", authentication.getName()));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication != null && authentication == UsernamePasswordAuthenticationToken.class;
	}

}
