package com.aii.platform.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aii.platform.models.CarteCapitol;
import com.aii.platform.models.CarteCompleta;
import com.aii.platform.response.Response;
import com.aii.platform.service.CarteCapitolService;

@Controller
public class CarteCapitolController {
	
	
	@Autowired
	private CarteCapitolService carteCapitolService;
	
	@RequestMapping(value="/carteCapitol/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCarteCompleta(@PathVariable("id")Long id) {
		carteCapitolService.deleteCarteCapitol(id);
		return new ResponseEntity<Response>(new Response("Carte capitol-sters"), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/carteCapitol/{id}", method = RequestMethod.GET)
	public ResponseEntity<CarteCapitol> getCarteCapitolyId(@PathVariable("id") Long id) {
		return new ResponseEntity<CarteCapitol>(carteCapitolService.getCarteCapitolById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/editCarteCapitol/{id}", method = RequestMethod.POST)
	public ResponseEntity<CarteCapitol> updateCarteCapitolaById(@PathVariable("id") Long id, HttpServletRequest request) {
		
		String editura = request.getParameter("editura-carte-capitol").replace("\"", "");
		String editie = request.getParameter("editie-carte-capitol").replace("\"", "");
		String anAparitieString = request.getParameter("an-aparitie-carte-capitol").replace("\"", "");
		String issn = request.getParameter("issn-carte-capitol").replace("\"", "");
		String isbn = request.getParameter("isbn-carte-capitol").replace("\"", "");
		int anAparitie = Integer.parseInt(anAparitieString);
		String title = request.getParameter("title").replace("\"", "");
		System.out.println(title);
		String abstractSection = request.getParameter("abstract-section").replace("\"", "");
		String wos = request.getParameter("wos").replace("\"", "");
		String doi = request.getParameter("doi").replace("\"", "");
		String numeCapitol = request.getParameter("nume-capitol").replace("\"", "");
		String pagIncep = request.getParameter("pagina-inceput").replace("\"", "");
		int paginaInceput = Integer.parseInt(pagIncep);
		String pagSfarsit = request.getParameter("pagina-sfarsit").replace("\"", "");
		int paginaSfarsit = Integer.parseInt(pagSfarsit);
		String titluCarte = request.getParameter("titlu-carte").replace("\"", "");
		String autoriCarte = request.getParameter("autori-carte").replace("\"", "");
		String editoriCarte = request.getParameter("editori-carte").replace("\"", "");
		
		carteCapitolService.updateCarteCapitol(id, titluCarte, autoriCarte, editoriCarte, numeCapitol, 
				paginaInceput, paginaSfarsit, editura, editie, isbn, issn, abstractSection, title, wos, doi);
		
		return new ResponseEntity<CarteCapitol>(carteCapitolService.getCarteCapitolById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
	
	

}
