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
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.response.Response;
import com.aii.platform.service.CarteCompletaService;
import com.aii.platform.service.UploadedArticleService;

@Controller
public class CarteCompletaController {
	
	@Autowired
	private CarteCompletaService carteCompletaService;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	@RequestMapping(value="/carteCompleta/test", method = RequestMethod.GET)
	public ResponseEntity<?> testCarteCompleta() {
		UploadedArticle article = uploadedArticleService.getArticleById(1L);
		CarteCompleta carteCompleta = new CarteCompleta("Minerva", "4", "1235-5622-56-12364", "1234-5678",2005);
		carteCompleta.setUploadedArticle(article);
		carteCompletaService.saveCarteCompleta(carteCompleta);
		return new ResponseEntity<Response>(new Response("Carte completa has been added"), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/carteCompleta/{id}", method = RequestMethod.GET)
	public ResponseEntity<CarteCompleta> getCarteCompletaById(@PathVariable("id") Long id) {
		return new ResponseEntity<CarteCompleta>(carteCompletaService.getCarteCompletaById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	

}
