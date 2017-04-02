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


@RestController
@RequestMapping(value="/user")
public class AppUserController {
	
	@Autowired
	private AppUserRepository appUserRepository;

	
	@RequestMapping(value="/all", method=RequestMethod.GET)	
	 public ResponseEntity getAllUsers() {
		return new ResponseEntity((List)appUserRepository.findAll(), new HttpHeaders(), HttpStatus.OK);
	  
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
		if(appUserRepository.findOne(id)!=null){
			return new ResponseEntity<AppUser>(appUserRepository.findOne(id), new HttpHeaders(), HttpStatus.OK);	
		} else {
			return new ResponseEntity<Response>(new Response("User couldn't be found"), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(value="/title/{title}", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthorByTitle(@PathVariable("title") String title) {
		if(appUserRepository.findByTitle(title)!=null) {
			return new ResponseEntity<List<AppUser>>((List)appUserRepository.findByTitle(title), new HttpHeaders(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response("User couldn't be found"), new HttpHeaders(), HttpStatus.NOT_FOUND);
			
		}
	}
	
	@RequestMapping(value="/article/{articleId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthorByArticleId(@PathVariable("articleId") String articleId) {
		int id = Integer.parseInt(articleId);
		if(appUserRepository.findByArticleId((long)id)!=null) {
			return new ResponseEntity<AppUser>(appUserRepository.findByArticleId((long)id), new HttpHeaders(), HttpStatus.OK);
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
		if(appUserRepository.findByUsername(userUsername) != null) {
			return new ResponseEntity<Response>(new Response("Username deja existent"), new HttpHeaders(), HttpStatus.IM_USED);
		} else
			if(appUserRepository.findByEmail(userEmail)!= null) {
				return new ResponseEntity<Response>(new Response("Email deja in uz"), new HttpHeaders(), HttpStatus.IM_USED);
			} else {
				appUserRepository.save(user);
				return new ResponseEntity<Response>(new Response("User has been created"), new HttpHeaders(), HttpStatus.OK);
				
			}		
	}
	
}
