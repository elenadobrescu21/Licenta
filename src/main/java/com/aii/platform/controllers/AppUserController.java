package com.aii.platform.controllers;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.response.BooleanResponse;
import com.aii.platform.response.Response;
import com.aii.platform.security.TokenUtils;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.UploadedArticleService;


@RestController
@RequestMapping(value="/user")
public class AppUserController {
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;

	
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
	
	@RequestMapping(value="/coauthorsByArticleId/{articleId}", method=RequestMethod.GET)
	public ResponseEntity<?> getCoauthorsByArticleId(@PathVariable(value="articleId")Long articleId) {
		List<AppUser> coauthorsForArticle = appUserService.getCoauthorsByArticleId(articleId);
		
		return new ResponseEntity<>(coauthorsForArticle, new HttpHeaders(), HttpStatus.OK);
		
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
	
	@RequestMapping(value="/checkIfArticleIsFavourited/{articleId}", method = RequestMethod.GET)
	public ResponseEntity<?>checkIfArticleIsFavourited(@PathVariable(value="articleId")Long articleId,
			HttpServletRequest request){
		
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		AppUser user = appUserService.getAppUserByUsername(username);
		
		boolean articleIsFavourited = false;
		
		Set<UploadedArticle> favouriteArticles = uploadedArticleService.getAllArticlesFavouritedByUser(user.getId());
		for(UploadedArticle u : favouriteArticles) {
			if(u.getUploadedArticleId().equals(articleId)){
				articleIsFavourited = true;
			}
		}
		
		return new ResponseEntity<BooleanResponse>(new BooleanResponse(articleIsFavourited),new HttpHeaders(), HttpStatus.OK);
		
	}
		
}
