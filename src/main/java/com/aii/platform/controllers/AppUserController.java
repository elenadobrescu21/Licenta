package com.aii.platform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.Response;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.repository.AppUserService;

@RestController
@RequestMapping(value="/user")
public class AppUserController {
	
	@Autowired
	private AppUserRepository appUserRepository;

	
	@RequestMapping(value="/all", method=RequestMethod.GET)	
	 public ResponseEntity getAllUsers() {
		return new ResponseEntity((List)appUserRepository.findAll(), new HttpHeaders(), HttpStatus.OK);
	  
	}
	
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public ResponseEntity getUserByUsername(@PathVariable("username") String username) {
		
	return new ResponseEntity<AppUser>(appUserRepository.findOneByUsername(username), new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity addNewUser(@RequestBody AppUser user) {
		String userUsername = user.getUsername();
		String userEmail = user.getEmail();
		appUserRepository.save(user);
		return new ResponseEntity<Response>(new Response("User has been created"), new HttpHeaders(), HttpStatus.OK);
		
	}
	

}
