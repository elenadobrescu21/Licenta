package com.aii.platform.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aii.platform.models.Response;
import com.aii.platform.models.UploadedArticle;

@Controller
public class UploadedArticleController {
	
	@RequestMapping(value = "/uploadArticle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadArticle(@RequestParam(value="title") String title,
			@RequestParam(value = "file") MultipartFile file,HttpServletRequest request) {
		
		  try {
		      // Get the filename and build the local file path
		      String filename = file.getOriginalFilename();
		      String directory = "upload-dir";
		      String filepath = Paths.get(directory, filename).toString();
		      
		      // Save the file locally
		      BufferedOutputStream stream =
		          new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		      stream.write(file.getBytes());
		      stream.close();
		    }
		    catch (Exception e) {
		      System.out.println(e.getMessage());
		      return new ResponseEntity<Response>(new Response("Couldn't upload file"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		    }
		    
		    System.out.println("File uploaded successfully: " + file.getOriginalFilename());
		    return new ResponseEntity<Response>(new Response("File uploaded successfully"), new HttpHeaders(), HttpStatus.OK);
		  } // method uploadFile

		
	}


