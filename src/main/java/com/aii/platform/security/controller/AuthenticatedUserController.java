package com.aii.platform.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aii.platform.security.TokenUtils;
import com.aii.platform.security.model.SpringSecurityUser;

@RestController
public class AuthenticatedUserController {
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@RequestMapping(value="/me", method = RequestMethod.GET)
	public SpringSecurityUser getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		SpringSecurityUser user = (SpringSecurityUser) userDetailsService.loadUserByUsername(username);
		return user;
			
	}
	
}
