package com.xiovr.unibot.data.service;

import com.xiovr.unibot.security.UserAuthenticationRequest;
import com.xiovr.unibot.security.UserAuthenticationResponse;
import com.xiovr.unibot.security.UserRoles;

public class UserService {
	public UserAuthenticationResponse authenticate(UserAuthenticationRequest request) {
		UserAuthenticationResponse response = new UserAuthenticationResponse();
		// FIXME Analize user request!
		// Only for test!
		response.setLogin(request.getLogin());
		response.setName(request.getName());
		response.setRole(UserRoles.USER);
		
		return response;
	}
}
