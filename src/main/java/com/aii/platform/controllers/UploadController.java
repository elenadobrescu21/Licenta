package com.aii.platform.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.Response;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.repository.UploadedArticleRepository;
import com.aii.platform.security.TokenUtils;
import com.aii.platform.utils.FileUtils;
import com.aii.platform.utils.PDFBoxUtils;
import com.aii.platform.utils.lucene.Indexer;

@Controller
public class UploadController {
	
	private static final String INDEX_DIR_PATH = "src/main/resources/static/file-storage/index-dir";
	private static final String UPLOAD_DIR = "src/main/resources/static/file-storage/uploads";
	private static final String TEMP_DIR = "src/main/resources/static/file-storage/recents";
	
	@Autowired
	private UploadedArticleRepository uploadedArticleRepository;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	FileUtils fileUtils;
	
	@Autowired
	Indexer indexer;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadArticle(@RequestParam(value="title") String title,
			@RequestParam(value = "file") MultipartFile file,HttpServletRequest request) throws UnsupportedEncodingException,
	FileNotFoundException {
		
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		AppUser user = appUserRepository.findByUsername(username);
		String titleToBeCompared = title.replace("\"", "");
		String titleToBeSaved = title.replace("\"", "");
//		
//		if(diacriticsUtils.checkForDiacritics(titleToBeCompared)) {
//			titleToBeCompared = diacriticsUtils.removeDiacritics(titleToBeCompared);
//		}
		//System.out.println("Title with removed diacritics:" + titleToBeCompared);
		
		if(uploadedArticleRepository.findByTitle(titleToBeCompared)!=null) {
			return new ResponseEntity<Response>(new Response("Titlu deja existent") , new HttpHeaders(), HttpStatus.IM_USED);
		} else {
		
		  try {
		      // Get the filename and build the local file path
		      String filename = file.getOriginalFilename();
		      String directory = "src/main/resources/static/file-storage/recents";
		      String filepath = Paths.get(TEMP_DIR, filename).toString();
		          
		      byte[] uploadedFile = file.getBytes();
		      
		      UploadedArticle articleToUpload = new UploadedArticle(titleToBeSaved,filename, uploadedFile);
		      user.getUploadedArticles().add(articleToUpload);
		      articleToUpload.setAppUser(user);
		      try {
		      appUserRepository.save(user);	      
		      uploadedArticleRepository.save(articleToUpload);
		      } catch (Exception e) {
		    	  e.printStackTrace();
		    	  System.out.println("Couldn't upload file to db");
		    	  return new ResponseEntity<Response>(new Response("Couldn't upload file to database"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		      }
		      
		      
		      // Save the file locally
		      BufferedOutputStream stream =
		          new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		      stream.write(file.getBytes());
		      stream.close();
		      
		     File fileToUpload = fileUtils.multipartToFile(file);
		      
		     PDFBoxUtils pdfUtils = new PDFBoxUtils();
		     String content = pdfUtils.getTextContentFromPDF(filepath);
		     System.out.println("Content:" + content);
		     System.out.println("Numarul de fisiere din director:" + fileUtils.countPDFsInDirectory(TEMP_DIR));
				 Indexer indexer = new Indexer();
				 indexer.createIndex(INDEX_DIR_PATH, fileToUpload, content);
			
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		      System.out.println(e.getMessage());
		      System.out.println("Couldn't upload file 1");
		      return new ResponseEntity<Response>(new Response("Couldn't upload file"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		    }
		  	
		  	if(!file.getContentType().equals("application/pdf")) {
		  		return new ResponseEntity<Response>(new Response("Only pdf accepted"), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
		  	}
		  	    
		  	System.out.println("File name: " + file.getName());
		  	System.out.println("Content type: " + file.getContentType());
		    System.out.println("File uploaded successfully: " + file.getOriginalFilename());
		    return new ResponseEntity<Response>(new Response("File uploaded successfully"), new HttpHeaders(), HttpStatus.OK);
		}
		
		} // method uploadFile

}
