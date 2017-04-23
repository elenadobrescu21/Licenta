package com.aii.platform.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.repository.UploadedArticleRepository;
import com.aii.platform.response.Response;
import com.aii.platform.security.TokenUtils;
import com.aii.platform.security.model.SpringSecurityUser;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.CoauthorService;
import com.aii.platform.service.TagService;
import com.aii.platform.service.UploadedArticleService;
import com.aii.platform.services.storage.StorageService;
import com.aii.platform.utils.DiacriticsUtils;

import com.aii.platform.models.*;

@Controller
public class UploadedArticleController {
	
	private static final String UPLOAD_DIR = "src/main/resources/static/file-storage/recents";
	
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private DiacriticsUtils diacriticsUtils;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired 
	private AppUserService appUserService;
	
	@Autowired
	private CoauthorService coauthorService;
	
		
	@RequestMapping(value="/downloadPDF/{articleId}", method = RequestMethod.GET, produces="application/pdf")
	public ResponseEntity<byte[]> getPDF(@PathVariable("articleId") String articleId) {
		FileInputStream fileStream;
		int id = Integer.parseInt(articleId);
		UploadedArticle downloadedArticle = uploadedArticleService.getArticleById(id);
		String filename = downloadedArticle.getFilename();
		String filepath = Paths.get(UPLOAD_DIR, filename).toString();
		try {
			fileStream = new FileInputStream(new File(filepath));
			byte[] contents = IOUtils.toByteArray(fileStream);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			headers.setContentDispositionFormData(filename, filename);
			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
			downloadedArticle.incrementNumberOfDownloads();
			uploadedArticleService.saveUploadedArticle(downloadedArticle);
			return response;
		}catch(FileNotFoundException e) {
			System.err.println(e);
		}catch(IOException e) {
			System.err.println(e);	
		}
		return null;
	}
	
			
		@RequestMapping(value="/article/{title}", method = RequestMethod.GET)
		public ResponseEntity<?> getArticleByTitle(@PathVariable("title") String title) {
			if(uploadedArticleService.getArticleByTitle(title)!=null) {
				return new ResponseEntity<UploadedArticle> (uploadedArticleService.getArticleByTitle(title), new HttpHeaders(), HttpStatus.OK);
				
			} else{
				return new ResponseEntity<Response>(new Response("File doesn't exist"), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			
		}
		
		@RequestMapping(value="/articleID/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getArticleById(@PathVariable("id") Long id) {
			if(uploadedArticleService.getArticleById(id)!=null) {
				return new ResponseEntity<UploadedArticle>(uploadedArticleService.getArticleById(id), new HttpHeaders(), HttpStatus.OK);
				
			} else {
				return new ResponseEntity<Response>(new Response("Article couldn't be found"), new HttpHeaders(), HttpStatus.OK);
			}
			
		}
		
				
		@RequestMapping(value="/latestArticles", method=RequestMethod.GET)
		public ResponseEntity<?> getLatestArticles() {
			if(uploadedArticleService.getLastThreeUploadedArticles()==null) {
				return new ResponseEntity<Response>(new Response("There are no articles yet"), new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
			return new ResponseEntity<>(uploadedArticleService.getLastThreeUploadedArticles(), new HttpHeaders(), HttpStatus.OK);
			}
		}
		
		@RequestMapping(value="/allArticles", method = RequestMethod.GET)
		public ResponseEntity<?> getAllArticles() {
			if(uploadedArticleService.getAllArticles() == null) {
				return new ResponseEntity<Response>(new Response("There are no articles yet"), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(uploadedArticleService.getAllArticles(), new HttpHeaders(), HttpStatus.OK);
		}
		
		
		@RequestMapping(value="/articleByAuthor/{authorId}", method = RequestMethod.GET)
		public ResponseEntity<?> getArticlesByAuthorId(@PathVariable("authorId") Long authorId) {
			if(uploadedArticleService.getArticlesByAppUserId(authorId)==null) {
				return new ResponseEntity<Response>(new Response("Acest autor inca nu a publicat niciun articol"), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(uploadedArticleService.getArticlesByAppUserId(authorId), new HttpHeaders(), HttpStatus.OK);
		}
		
		@RequestMapping(value="/articleByCoauthor/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getAllArticlesInCollaborationByAppUserId(@PathVariable("id") Long coauthorId) {
			if(uploadedArticleService.getAllArticlesInCollaborationByAppUserId(coauthorId)==null) {
				return new ResponseEntity<Response>(new Response("Acest autor nu are articole in colaborare"), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(uploadedArticleService.getAllArticlesInCollaborationByAppUserId(coauthorId), new HttpHeaders(), HttpStatus.OK);
		}
		
		@RequestMapping(value="/testFavourite", method=RequestMethod.GET)
		public ResponseEntity<?> testFavourite() {
			AppUser user = appUserService.getAppUserById(1L);
			UploadedArticle article = uploadedArticleService.getArticleById(1L);
			user.addArticleToFavourites(article);
			article.addToUsersWhoFavourited(user);
			appUserService.saveUser(user);
			uploadedArticleService.saveUploadedArticle(article);
			return new ResponseEntity<Response>(new Response("Article added to favourites"), new HttpHeaders(), HttpStatus.OK);
		}
		
		@RequestMapping(value="/testCoauthorWithoutArticle", method=RequestMethod.GET)
		public ResponseEntity<?> testCoauthorWithoutAccount() {
			UploadedArticle article = uploadedArticleService.getArticleById(1);
			System.out.println("article name: "+ article.getTitle());
			Coauthor coauthor = new Coauthor("Ion Ion");
			article.addCoauthorWithoutAccount(coauthor);
			//coauthor.adaugaArticol(article);
			uploadedArticleService.saveUploadedArticle(article);
			return new ResponseEntity<Response>(new Response("Reusit"), new HttpHeaders(), HttpStatus.OK);
		}
		
		@RequestMapping(value="/findByTag/{tag}", method=RequestMethod.GET)
		public ResponseEntity<?> findByTag(@PathVariable(value="tag")String tag){
			List<UploadedArticle> articlesByTag = uploadedArticleService.getAllArticlesByDenumireTag(tag);
			return new ResponseEntity<List<UploadedArticle>>(articlesByTag, new HttpHeaders(), HttpStatus.OK);
		}
		
		@RequestMapping(value="/addToFavourites/{articleId}", method = RequestMethod.POST) 
		public ResponseEntity<?> addArticleToFavourites(@PathVariable(value="articleId") Long articleId, HttpServletRequest request) {
			
			String token = request.getHeader("X-Auth-Token");
			
			String username = tokenUtils.getUsernameFromToken(token);
			AppUser user = appUserService.getAppUserByUsername(username);
			
			UploadedArticle article = uploadedArticleService.getArticleById(articleId);
			Set<UploadedArticle> articlesFavouritedByUser = uploadedArticleService.getAllArticlesFavouritedByUser(user.getId());
			int setSizeBeforeAdding = articlesFavouritedByUser.size();
			
			user.addArticleToFavourites(article);
			article.addToUsersWhoFavourited(user);
			
			int setSizeAfterAdding = user.getFavouriteArticles().size();
			System.out.println("Username:" + user.getUsername());
			System.out.println("Article: " + article.getTitle());
			if(setSizeAfterAdding>setSizeBeforeAdding) {
				appUserService.saveUser(user);
				uploadedArticleService.saveUploadedArticle(article);
			}
			
		
		return new ResponseEntity<Response>(new Response("Article has been added to favourites"), new HttpHeaders(), HttpStatus.OK);
	}

}
