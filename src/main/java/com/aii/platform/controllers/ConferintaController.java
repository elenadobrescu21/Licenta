package com.aii.platform.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	@RequestMapping(value="/conferinta/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteConferinta(@PathVariable("id")Long id) {
		conferintaService.deleteConferinta(id);
		return new ResponseEntity<Response>(new Response("Conferinta-sters"), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/conferinta/an/{an}", method = RequestMethod.GET)
	public ResponseEntity<List<Conferinta>> getConferintaByYear(@PathVariable("an")int year) {
		return new ResponseEntity<List<Conferinta>>(conferintaService.getConferintaByYear(year),new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/conferinta/{id}", method = RequestMethod.GET)
	public ResponseEntity<Conferinta> getConferintaById(@PathVariable("id") Long id) {
		return new ResponseEntity<Conferinta>(conferintaService.getConferintaById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/editConferinta/{id}", method = RequestMethod.POST)
	public ResponseEntity<Conferinta> updateConferintaById(@PathVariable("id") Long id, HttpServletRequest request) {
		
		String numeConferinta = request.getParameter("nume-conferinta").replace("\"", "");
		String locatieConferinta = request.getParameter("locatie-conferinta").replace("\"", "");
		String dataConf = request.getParameter("data-conferinta").replace("\"", "");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dataConferinta = null;
		try {
			dataConferinta = format.parse(dataConf);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = request.getParameter("title").replace("\"", "");

		String abstractSection = request.getParameter("abstract-section").replace("\"", "");
		String wos = request.getParameter("wos").replace("\"", "");
		String doi = request.getParameter("doi").replace("\"", "");
		
		conferintaService.updateConferinta(id, numeConferinta, locatieConferinta, dataConferinta, abstractSection, title, wos, doi);
		
		return new ResponseEntity<Conferinta>(conferintaService.getConferintaById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	

}
