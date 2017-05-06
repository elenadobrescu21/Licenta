package com.aii.platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aii.platform.models.CarteCompleta;
import com.aii.platform.models.Conferinta;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.response.Response;
import com.aii.platform.service.ConferintaService;
import com.aii.platform.service.UploadedArticleService;

@Controller
public class ConferintaController {
	
	@Autowired
	private ConferintaService conferintaService;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	@RequestMapping(value="/conferinta/test", method = RequestMethod.GET)
	public ResponseEntity<?> addConferintaTest(){
		UploadedArticle article = uploadedArticleService.getArticleById(1L);
		Conferinta conferinta = new Conferinta("Sesiunea de comunicari", "Universitatea Politehnica Bucuresti");
		conferinta.setUploadedArticle(article);
		conferintaService.saveConferinta(conferinta);
		return new ResponseEntity<Response>(new Response("Conferinta has been added"), new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/conferinta/{id}", method = RequestMethod.GET)
	public ResponseEntity<Conferinta> getConferintaById(@PathVariable("id") Long id) {
		return new ResponseEntity<Conferinta>(conferintaService.getConferintaById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	

}
