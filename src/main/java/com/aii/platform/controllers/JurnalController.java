package com.aii.platform.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.aii.platform.models.JurnalRevista;
import com.aii.platform.response.Response;
import com.aii.platform.service.JurnalRevistaService;

@Controller
public class JurnalController {
	
	@Autowired
	private JurnalRevistaService jurnalRevistaService;
	
	
	@RequestMapping(value="/jurnalRevista/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCarteCompleta(@PathVariable("id")Long id) {
		jurnalRevistaService.deleteJurnalRevista(id);
		return new ResponseEntity<Response>(new Response("Carte completa-sters"), new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value="/jurnalRevista/{id}", method = RequestMethod.GET)
	public ResponseEntity<JurnalRevista> getJurnalRevistaById(@PathVariable("id") Long id) {
		return new ResponseEntity<JurnalRevista>(jurnalRevistaService.getJurnalRevistaById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/editJurnalRevista/{id}", method = RequestMethod.POST)
	public ResponseEntity<JurnalRevista> updateJurnalRevistaId(@PathVariable("id") Long id, HttpServletRequest request) {
		
		String titluJurnalRevista = request.getParameter("titlu-jurnal-revista").replace("\"", "");
		String nrJurRev = request.getParameter("numar-jurnal-revista").replace("\"", "");
		String volJurRev = request.getParameter("volum-jurnal-revista").replace("\"", "");
		String dataAp = request.getParameter("data-aparitie").replace("\"", "");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dataAparitie = null;
		try {
			dataAparitie = format.parse(dataAp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int nrJurnalRevista = Integer.parseInt(nrJurRev);
		int volJurnalRevista = Integer.parseInt(volJurRev);
		
		String issn = request.getParameter("issn-jurnal-revista").replace("\"", "");
		String isbn = request.getParameter("isbn-jurnal-revista").replace("\"", "");
	
		String pagStart = request.getParameter("pagina-inceput").replace("\"", "");
		String pagSfarsit = request.getParameter("pagina-sfarsit").replace("\"", "");
		
		int paginaStart = Integer.parseInt(pagStart);
		int paginaSfarsit = Integer.parseInt(pagSfarsit);
		String title = request.getParameter("title").replace("\"", "");
		System.out.println(title);
		String abstractSection = request.getParameter("abstract-section").replace("\"", "");
		String wos = request.getParameter("wos").replace("\"", "");
		String doi = request.getParameter("doi").replace("\"", "");
		
		jurnalRevistaService.updateJurnalRevista(id,titluJurnalRevista, nrJurnalRevista, volJurnalRevista, paginaStart, paginaSfarsit,issn, isbn,  dataAparitie, abstractSection, title, wos, doi);
		
		return new ResponseEntity<JurnalRevista>(jurnalRevistaService.getJurnalRevistaById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	
}
