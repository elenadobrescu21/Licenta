package com.aii.platform.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

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

import com.aii.platform.models.AppUser;
import com.aii.platform.models.Response;
import com.aii.platform.models.Tag;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.repository.UploadedArticleRepository;
import com.aii.platform.security.TokenUtils;
import com.aii.platform.security.model.SpringSecurityUser;
import com.aii.platform.service.TagService;
import com.aii.platform.service.UploadedArticleService;
import com.aii.platform.services.storage.StorageService;
import com.aii.platform.utils.DiacriticsUtils;

@Controller
public class UploadedArticleController {
	
	private static final String UPLOAD_DIR = "src/main/resources/static/file-storage/uploads";
	
	@Autowired
	private UploadedArticleRepository uploadedArticleRepository;
	
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
	
	
	@RequestMapping(value="/downloadPDF/{articleId}", method = RequestMethod.GET, produces="application/pdf")
	public ResponseEntity<byte[]> getPDF(@PathVariable("articleId") String articleId) {
		FileInputStream fileStream;
		int id = Integer.parseInt(articleId);
		UploadedArticle downloadedArticle = uploadedArticleRepository.findOne((long) id);
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
			uploadedArticleRepository.save(downloadedArticle);
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
			if(uploadedArticleRepository.findByTitle(title)!= null) {
				return new ResponseEntity<UploadedArticle> (uploadedArticleRepository.findByTitle(title), new HttpHeaders(), HttpStatus.OK);
				
			} else{
				return new ResponseEntity<Response>(new Response("File doesn't exist"), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			
		}
		
		@RequestMapping(value="/articleID/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getArticleById(@PathVariable("id") Long id) {
			if(uploadedArticleRepository.findOne(id) != null) {
				return new ResponseEntity<UploadedArticle>(uploadedArticleRepository.findOne(id), new HttpHeaders(), HttpStatus.OK);
				
			} else {
				return new ResponseEntity<Response>(new Response("Article couldn't be found"), new HttpHeaders(), HttpStatus.OK);
			}
			
		}
		
		@RequestMapping(value="/maxId", method=RequestMethod.GET)
		public ResponseEntity<?> getMaxId() {
			return new ResponseEntity<>(uploadedArticleRepository.getMaxId(), new HttpHeaders(), HttpStatus.OK);
		}
		
		@RequestMapping(value="/latestArticles", method=RequestMethod.GET)
		public ResponseEntity<?> getLatestArticles() {
			if(uploadedArticleRepository.findFirst3ByOrderByUploadedArticleIdDesc()==null) {
				return new ResponseEntity<Response>(new Response("There are no articles yet"), new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
			return new ResponseEntity<>(uploadedArticleRepository.findFirst3ByOrderByUploadedArticleIdDesc(), new HttpHeaders(), HttpStatus.OK);
			}
		}
		
		@RequestMapping(value="/allArticles", method = RequestMethod.GET)
		public ResponseEntity<?> getAllArticles() {
			if(uploadedArticleRepository.findAll() == null) {
				return new ResponseEntity<Response>(new Response("There are no articles yet"), new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(uploadedArticleRepository.findAll(), new HttpHeaders(), HttpStatus.OK);
		}
		
		@RequestMapping(value="/testTag", method = RequestMethod.GET)
		public ResponseEntity<?> associateArticleWithTag(){
			UploadedArticle article = uploadedArticleService.getArticleById(1);
			Tag tag = tagService.getTagByDenumire("poo");
			article.addTag(tag);
			tag.addArticle(article);
			tagService.saveTag(tag);
			
			return new ResponseEntity<>(new Response("Asociere terminata"), new HttpHeaders(),HttpStatus.OK);
			
		}
		
//		@RequestMapping(value="/articleByAuthor/{authorId}", method = RequestMethod.GET)
//		public ResponseEntity<?> getArticlesByAuthorId(@PathVariable("authorId") Long authorId) {
//			if(uploadedArticleRepository.findByAppUserId(authorId)==null) {
//				return new ResponseEntity<Response>(new Response("Acest autor inca nu a publicat niciun articol"), new HttpHeaders(), HttpStatus.NOT_FOUND);
//			}
//			return new ResponseEntity<>(uploadedArticleRepository.findByAppUserId(authorId), new HttpHeaders(), HttpStatus.OK);
//		}
//		
	}


