package com.aii.platform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aii.platform.models.UploadedArticle;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.repository.UploadedArticleRepository;
import com.aii.platform.response.Response;

@Controller
public class UploadedArticleTestController {
	
	@Autowired
	private UploadedArticleRepository uploadedArticleRepository;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@RequestMapping(value="/test/findByArticleTypeAndTitle", method=RequestMethod.GET)
	public ResponseEntity<?> testOne() {
		String title = "Rezolvare";
		Long id = 3L;
		List<UploadedArticle> result = uploadedArticleRepository.findByArticleTypeAndTitle(id, title);
		return new ResponseEntity<List<UploadedArticle>>(result, new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/test/findByTipArticolOwnerAutor", method=RequestMethod.GET)
	public ResponseEntity<?> testTwo() {
		String nume = "Dobrescu";
		String prenume = "Elena";
		Long id = 3L;
		List<UploadedArticle> result = uploadedArticleRepository.findByTipArticolOwnerAutor(id, nume, prenume);
		return new ResponseEntity<List<UploadedArticle>>(result, new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/test/selectNumePrenume", method=RequestMethod.GET)
	public ResponseEntity<?> testThree() {
	
		return new ResponseEntity<List<?>>(appUserRepository.findAllByNumePrenume(), new HttpHeaders(), HttpStatus.OK);
		
	}
	
	
//	@RequestMapping(value="/test/findConferintaB", method=RequestMethod.GET)
//	public ResponseEntity<?> testThree() {		
//		int year = 2017;
//		List<UploadedArticle> result = uploadedArticleRepository.findConferintaByYear(2017);
//		return new ResponseEntity<List<UploadedArticle>>(result, new HttpHeaders(), HttpStatus.OK);
//		
//	}
	
	

}
