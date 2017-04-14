package com.aii.platform.controllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.Response;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.service.AppUserService;


@RestController
@RequestMapping(value="/user")
public class AppUserController {
	
	@Autowired
	private AppUserService appUserService;

	
	@RequestMapping(value="/all", method=RequestMethod.GET)	
	 public ResponseEntity<?> getAllUsers() {
		
		if(appUserService.getAllUsers() != null) {
			return new ResponseEntity<List<AppUser>>(appUserService.getAllUsers(), new HttpHeaders(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Response>(new Response("No users"), new HttpHeaders(), HttpStatus.NO_CONTENT);
		}
		  
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
		if(appUserService.getAppUserById(id)!=null){
			return new ResponseEntity<AppUser>(appUserService.getAppUserById(id), new HttpHeaders(), HttpStatus.OK);	
		} else {
			return new ResponseEntity<Response>(new Response("User couldn't be found"), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public ResponseEntity<?> test() {
		AppUser appuser = appUserService.getAppUserById(1);
		appuser.setPrenume("Elena Alexandra");
		appUserService.saveUser(appuser);
		return new ResponseEntity<>(new Response("App user modified"), new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/article/{articleId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthorByArticleId(@PathVariable("articleId") String articleId) {
		int id = Integer.parseInt(articleId);
		if(appUserService.getAppUserByUploadedArticleId((long)id)!=null) {
			return new ResponseEntity<AppUser>(appUserService.getAppUserByUploadedArticleId((long)id), new HttpHeaders(), HttpStatus.OK);
		} else{
			return new ResponseEntity<Response>(new Response("User couldn't be found"), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity addNewUser(@RequestBody AppUser user) {
		String userUsername = user.getUsername();
		String userEmail = user.getEmail();
		String userPassword = user.getPassword();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(userPassword);
		user.setPassword(hashedPassword);
		if(appUserService.getAppUserByUsername(userUsername) != null) {
			return new ResponseEntity<Response>(new Response("Username deja existent"), new HttpHeaders(), HttpStatus.IM_USED);
		} else
			if(appUserService.getAppUserByEmail(userEmail)!= null) {
				return new ResponseEntity<Response>(new Response("Email deja in uz"), new HttpHeaders(), HttpStatus.IM_USED);
			} else {
				appUserService.saveUser(user);
				return new ResponseEntity<Response>(new Response("User has been created"), new HttpHeaders(), HttpStatus.OK);		
			}	

		}
		
}
