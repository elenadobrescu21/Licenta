package com.aii.platform.security.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aii.platform.models.Response;
import com.aii.platform.security.TokenUtils;
import com.aii.platform.security.model.AuthenticationRequest;
import com.aii.platform.security.model.AuthenticationResponse;
import com.aii.platform.security.model.SpringSecurityUser;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@RequestMapping(method = RequestMethod.POST) 
	public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
	throws AuthenticationException {
		
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()
						)
				);
	   SecurityContextHolder.getContext().setAuthentication(authentication);
	   
	   UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	   String token = this.tokenUtils.generateToken(userDetails);
	   if(userDetails != null) {
	   return ResponseEntity.ok(new AuthenticationResponse(token));
	   } else {
		   return new ResponseEntity<Response>(new Response("Username or password incorrect"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	   }
	   				
	}
	
	@RequestMapping(value="refresh", method = RequestMethod.GET)
	public ResponseEntity<?> authenticationRequest(HttpServletRequest request){
		String token = request.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(token);
		SpringSecurityUser user = (SpringSecurityUser) this.userDetailsService.loadUserByUsername(username);
		if(this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
			String refreshedToken = this.tokenUtils.refreshToken(token);
			return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
}
