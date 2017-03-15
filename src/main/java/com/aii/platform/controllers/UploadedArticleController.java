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
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.repository.UploadedArticleRepository;
import com.aii.platform.security.TokenUtils;
import com.aii.platform.security.model.SpringSecurityUser;
import com.aii.platform.services.storage.StorageService;
import com.aii.platform.utils.DiacriticsUtils;

@Controller
public class UploadedArticleController {
	
	@Autowired
	private UploadedArticleRepository uploadedArticleRepository;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private DiacriticsUtils diacriticsUtils;
	
	
	@RequestMapping(value="/downloadPDF", method = RequestMethod.GET, produces="application/pdf")
	public ResponseEntity<byte[]> getPDF() {
		FileInputStream fileStream;
		try {
			fileStream = new FileInputStream(new File("src/main/resources/static/uploads/Date_examene_IS.pdf"));
			byte[] contents = IOUtils.toByteArray(fileStream);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename ="Date_examene_IS";
			headers.setContentDispositionFormData(filename, filename);
			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
			return response;
		}catch(FileNotFoundException e) {
			System.err.println(e);
		}catch(IOException e) {
			System.err.println(e);	
		}
		return null;
	}
	
	
	@RequestMapping(value = "/uploadArticle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadArticle(@RequestParam(value="title") String title,
			@RequestParam(value = "file") MultipartFile file,HttpServletRequest request) throws UnsupportedEncodingException,
	FileNotFoundException {
		
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		AppUser user = appUserRepository.findByUsername(username);
		String titleToBeCompared = title.replace("\"", "");
		String titleToBeSaved = title.replace("\"", "");
		
		if(diacriticsUtils.checkForDiacritics(titleToBeCompared)) {
			titleToBeCompared = diacriticsUtils.removeDiacritics(titleToBeCompared);
		}
		System.out.println("Title with removed diacritics:" + titleToBeCompared);
		
		if(uploadedArticleRepository.findByTitle(titleToBeCompared)!=null) {
			return new ResponseEntity<Response>(new Response("Titlu deja existent") , new HttpHeaders(), HttpStatus.IM_USED);
		} else {
		
		  try {
		      // Get the filename and build the local file path
		      String filename = file.getOriginalFilename();
		      String directory = "src/main/resources/static/uploads";
		      String filepath = Paths.get(directory, filename).toString();
		      
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
		      
		      try {
			      PDDocument document = null;
			      document = PDDocument.load(new File(filepath));
			      document.getClass();
			      if(!document.isEncrypted()) {
			    	  PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			    	  stripper.setSortByPosition(true);
			    	  PDFTextStripper Tstripper = new PDFTextStripper();
			    	  int numberOfPages = document.getNumberOfPages();
			    	  Tstripper.setStartPage(1);
			    	  Tstripper.setEndPage(numberOfPages);
			    	  Tstripper.getArticleStart();
			    	  String st = new String(Tstripper.getText(document).getBytes(), "UTF-8");
			    	  String st2 = Tstripper.getArticleStart();
			    	  System.out.println("Text:" + st);
			    	  System.out.println("Lungimea textului " + st.length());
			    	   	  
			      }
			      } catch(Exception e) {
			    	  e.printStackTrace();
			      }
		    }
		    catch (Exception e) {
		      System.out.println(e.getMessage());
		      System.out.println("Couldn't upload file 1");
		      return new ResponseEntity<Response>(new Response("Couldn't upload file"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		    }
		  	
		  	if(!file.getContentType().equals("application/pdf")) {
		  		return new ResponseEntity<Response>(new Response("Only pdf accepted"), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
		  	}
		  	    
		  	//System.out.println("File name: " + file.getName());
		  	System.out.println("Content type: " + file.getContentType());
		    System.out.println("File uploaded successfully: " + file.getOriginalFilename());
		    return new ResponseEntity<Response>(new Response("File uploaded successfully"), new HttpHeaders(), HttpStatus.OK);
		}
		
		} // method uploadFile
		

		
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
				return new ResponseEntity<Response>(new Response("File couldn't be found"), new HttpHeaders(), HttpStatus.OK);
			}
			
		}
	}


