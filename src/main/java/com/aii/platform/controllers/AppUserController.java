package com.aii.platform.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	
	@RequestMapping(value="/allByTipArticol/{id}", method=RequestMethod.GET)	
	 public ResponseEntity<?> getAllUsersByTipArticol(@PathVariable("id")int id) {	
		Long newId = 0L;	
		if(id == 1){
			newId = 1L;
		}	
		if(id == 2) {
			newId = 2L;
		}	
		if(id == 3) {
			newId = 3L;
		}	
		if(id == 4){
			newId = 4L;
		}
		
		if(appUserService.getAllUsersByTipArticol(newId) != null) {
			return new ResponseEntity<List<AppUser>>(appUserService.getAllUsersByTipArticol(newId), new HttpHeaders(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Response>(new Response("No users"), new HttpHeaders(), HttpStatus.NO_CONTENT);
		}	  
	}
	
	@RequestMapping(value="/countArticles/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> countArticlesForUser(@PathVariable("userId")Long userId) {
		AppUser user = appUserService.getAppUserById(userId);
		int numberOfArticles = user.getUploadedArticles().size();
		
		return new ResponseEntity<Integer>(numberOfArticles, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/countArticlesByType/{usedId}/{articleTypeId}", method = RequestMethod.GET)
	public ResponseEntity<?> countArticlesForUserByArticleType(@PathVariable("userId")Long userId, @PathVariable("articleTypeId")Long articleTypeId) {
		AppUser user = appUserService.getAppUserById(userId);
		int numberOfArticles = 0;
		for(UploadedArticle u : user.getUploadedArticles()) {
			if(u.getTipArticol().getId() == articleTypeId) {
				numberOfArticles ++;
			}
		}
		
		return new ResponseEntity<Integer>(numberOfArticles, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/countArticlesByYear/{userId}/{year}", method = RequestMethod.GET)
	public ResponseEntity<?> countArticlesForUserByYear(@PathVariable("userId")Long userId, @PathVariable("year")int year) {
		AppUser user = appUserService.getAppUserById(userId);
		int numberOfArticles = 0;
		for(UploadedArticle u : user.getUploadedArticles()) {
			if(u.getUploadedOn().getYear() == year)  {
				numberOfArticles ++;
			}
		}
		return new ResponseEntity<Integer>(numberOfArticles, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/topUserByArticleType/{articleTypeId}", method = RequestMethod.GET)
	public ResponseEntity<?> topUserByArticleType(@PathVariable("articleTypeId") Long articleTypeId) {
		Map<Long, Integer> correspondenceMap = new HashMap<>();
		
		List<AppUser> allUsers = appUserService.getAllUsers();
		for(AppUser a: allUsers) {
			int numberOfArticles = 0;
			for(UploadedArticle u : a.getUploadedArticles()) {
				if(u.getTipArticol().getId() == articleTypeId) {
					numberOfArticles++;
				}
			}
			correspondenceMap.put(a.getId(), numberOfArticles);
		}
		
		Iterator it = correspondenceMap.entrySet().iterator();
		int max = -1;
		Long key = 0L;
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if((int) pair.getValue() > max) {
	        	max = (int) pair.getValue();
	        	key = (Long) pair.getKey();
	        }
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    return new ResponseEntity<AppUser>(appUserService.getAppUserById(key), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/topUserByYear/{year}", method = RequestMethod.GET)
	public ResponseEntity<?> topUserByYear(@PathVariable("year") Long year) {
		Map<Long, Integer> correspondenceMap = new HashMap<>();
		
		List<AppUser> allUsers = appUserService.getAllUsers();
		for(AppUser a: allUsers) {
			int numberOfArticles = 0;
			for(UploadedArticle u : a.getUploadedArticles()) {
				if(u.getUploadedOn().getYear() == year) {
					numberOfArticles++;
				}
			}
			correspondenceMap.put(a.getId(), numberOfArticles);
		}
		
		Iterator it = correspondenceMap.entrySet().iterator();
		int max = -1;
		Long key = 0L;
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if((int) pair.getValue() > max) {
	        	max = (int) pair.getValue();
	        	key = (Long) pair.getKey();
	        }
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    return new ResponseEntity<AppUser>(appUserService.getAppUserById(key), new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/allConferinta", method=RequestMethod.GET)	
	 public ResponseEntity<?> getAllUsersForConferinta() {
		
		if(appUserService.getAllUsersForConferinta() != null) {
			return new ResponseEntity<List<AppUser>>(appUserService.getAllUsersForConferinta(), new HttpHeaders(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Response>(new Response("No users"), new HttpHeaders(), HttpStatus.NO_CONTENT);
		}
		  
	}
	
	@RequestMapping(value="/favouriteArticles/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getUserFavouriteArticles(@PathVariable("id") Long id) {
		AppUser appUser = appUserService.getAppUserById(id);
		return new ResponseEntity<Set<UploadedArticle>>(appUser.getFavouriteArticles(), new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
		if(appUserService.getAppUserById(id)!=null){
			return new ResponseEntity<AppUser>(appUserService.getAppUserById(id), new HttpHeaders(), HttpStatus.OK);	
		} else {
			return new ResponseEntity<Response>(new Response("User couldn't be found"), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(value="/authenticated", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		if(appUserService.getAppUserByUsername(username)!=null) {
			return new ResponseEntity<AppUser>(appUserService.getAppUserByUsername(username), new HttpHeaders(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response("User details couldn't be retrieved"), new HttpHeaders(), HttpStatus.NOT_FOUND);
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
