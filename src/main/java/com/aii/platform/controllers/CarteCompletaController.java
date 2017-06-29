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
	
	@RequestMapping(value="/carteCompleta/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCarteCompleta(@PathVariable("id")Long id) {
		carteCompletaService.deleteCarteCompleta(id);
		return new ResponseEntity<Response>(new Response("Carte completa-sters"), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/carteCompleta/{id}", method = RequestMethod.GET)
	public ResponseEntity<CarteCompleta> getCarteCompletaById(@PathVariable("id") Long id) {
		return new ResponseEntity<CarteCompleta>(carteCompletaService.getCarteCompletaById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/editCarteCompleta/{id}", method = RequestMethod.POST)
	public ResponseEntity<CarteCompleta> updateCarteCompletaById(@PathVariable("id") Long id, HttpServletRequest request) {
		
		String editura = request.getParameter("editura-carte-completa").replace("\"", "");
		String editie = request.getParameter("editie-carte-completa").replace("\"", "");
		String anAparitieString = request.getParameter("an-aparitie-carte-completa").replace("\"", "");
		String issn = request.getParameter("issn-carte-completa").replace("\"", "");
		String isbn = request.getParameter("isbn-carte-completa").replace("\"", "");
		int anAparitie = Integer.parseInt(anAparitieString);
		String title = request.getParameter("title").replace("\"", "");
		System.out.println(title);
		String abstractSection = request.getParameter("abstract-section").replace("\"", "");
		String wos = request.getParameter("wos").replace("\"", "");
		String doi = request.getParameter("doi").replace("\"", "");
		
		carteCompletaService.updateCarteCompleta(id, editura, editie, isbn, issn, anAparitie, abstractSection, title, wos, doi);
		
		return new ResponseEntity<CarteCompleta>(carteCompletaService.getCarteCompletaById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
//	@RequestMapping(value="/testUpdateCarteCompleta/{id}",method = RequestMethod.POST) 
//	public ResponseEntity<CarteCompleta> updateCarteCompleta(@PathVariable("id") Long id) {
//		String abstractSection = "this is an example of abstract, new abstract. Lalalalalalala, gotta make 50 characters" +
//	"I wonder if everything is okay. lalallala";
//		carteCompletaService.updateCarteCompleta(id, "noua editura", "noua editie", "982712-3221-321", "2123-1223", 2009, abstractSection);
//		
//		return new ResponseEntity<CarteCompleta>(carteCompletaService.getCarteCompletaById(id), new HttpHeaders(), HttpStatus.OK);
//	}
	
	

}
