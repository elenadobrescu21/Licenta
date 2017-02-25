package com.aii.platform.controllers;

import javax.ws.rs.Consumes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aii.platform.models.Response;
import com.aii.platform.services.storage.StorageFileNotFoundException;
import com.aii.platform.services.storage.StorageService;

@Controller
public class FileUploadController {
	
	private final StorageService storageService;
	
	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;		
	}
	
	
	 @PostMapping("/upload")
	 public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {

	     storageService.store(file);
	     System.out.println("You have successfully uploaded the file " + file.getOriginalFilename() + "!");
	     return new ResponseEntity<Response>(new Response("File uploaded successfully"), new HttpHeaders(), HttpStatus.OK);
	        
	}
	 
	 @ExceptionHandler(StorageFileNotFoundException.class) 
	 public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
		 return ResponseEntity.notFound().build();
	 }
	
	
}
