package com.aii.platform.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aii.platform.models.CarteCapitol;
import com.aii.platform.models.CarteCompleta;
import com.aii.platform.models.Conferinta;
import com.aii.platform.models.JurnalRevista;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.response.Response;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.CarteCapitolService;
import com.aii.platform.service.CarteCompletaService;
import com.aii.platform.service.ConferintaService;
import com.aii.platform.service.JurnalRevistaService;
import com.aii.platform.service.TagService;
import com.aii.platform.service.TipArticolService;
import com.aii.platform.service.UploadedArticleService;

@Controller
public class QueryController {
	
	@Autowired
	private CarteCapitolService carteCapitolService;
	
	@Autowired
	private CarteCompletaService carteCompletaService;
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private ConferintaService conferintaService;
	
	@Autowired
	private JurnalRevistaService jurnalRevistaService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private TipArticolService tipArticolService;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	
	public List<UploadedArticle> getArticoleCarteCompleta() {
		return uploadedArticleService.getArticlesByTipArticolId(1L);
	}
	
	public List<UploadedArticle> getArticoleCarteCapitol() {
		return uploadedArticleService.getArticlesByTipArticolId(2L);
	}
	
	public List<UploadedArticle> getArticoleConferinta() {
		return uploadedArticleService.getArticlesByTipArticolId(3L);
	}
	
	public List<UploadedArticle> getArticoleJurnalRevista() {
		return uploadedArticleService.getArticlesByTipArticolId(4L);
	}
	
	public List<UploadedArticle> getArticoleByTitlu(String title) {
		return uploadedArticleService.getArticleByTitle(title);
	}
	
	public List<UploadedArticle> getArticoleByTag(String tag) {
		return uploadedArticleService.getAllArticlesByDenumireTag(tag);
	}
	
	public List<Conferinta> getConferintaByYear(int year) {
		return conferintaService.getConferintaByYear(year);
	}
	
	public List<JurnalRevista> getJurnalRevistaByYear(int year) {
		return jurnalRevistaService.getJurnalRevistaByYear(year);
	}
	
	public List<CarteCapitol> getCarteCapitolByYear(int year) {
		return carteCapitolService.getCarteCapitolByYear(year);
	}
	
	public List<CarteCompleta> getCarteCompletaByYear(int year) {
		return carteCompletaService.getCarteCompletaByYear(year);
	}
	
	public List<UploadedArticle> getArticlesByOwnerFullname(String nume, String prenume) {
		return uploadedArticleService.getArticlesByOwnerNumePrenume(nume, prenume);
	}
	
	public List<UploadedArticle> getArticlesByCoauthorFullname(String nume, String prenume) {
		return uploadedArticleService.getArticlesByCoauthorNumePrenume(nume, prenume);
	}
	
 	 @RequestMapping(value="/query", method =RequestMethod.POST)
	 public ResponseEntity<?> searchArticle(HttpServletRequest request) {
		 
		 String jurnal = request.getParameter("checkbox-jurnal");
		 String carteCapitol = request.getParameter("checkbox-carte-capitol");
		 String carteCompleta = request.getParameter("checkbox-carte-completa");
		 String conferinta = request.getParameter("checkbox-conferinta");
		 
		 String title = request.getParameter("title").replace("\"", "");;
		 String author = request.getParameter("author").replace("\"", "");;
		 String tag = request.getParameter("tag").replace("\"", "");;
		 String an = request.getParameter("an").replace("\"", "");
		 
		 if(jurnal.equals("true")) {
			 List<UploadedArticle> jurnale = this.getArticoleJurnalRevista();
			 List<Long> jurnaleIds = new ArrayList<Long>();
			 for(UploadedArticle j : jurnale) {
				 jurnaleIds.add(j.getUploadedArticleId());
			 }
			 
		 }
		 
		 if(carteCapitol.equals("true")) {
			 List<UploadedArticle> carteCapitolList = this.getArticoleCarteCapitol();
			 List<Long> carteCapitolIds = new ArrayList<Long>();
			 for(UploadedArticle ca : carteCapitolList) {
				 carteCapitolIds.add(ca.getUploadedArticleId());
			 }
		 }
		 
		 if(carteCompleta.equals("true")) {
			 List<UploadedArticle> carteCompletaList = this.getArticoleCarteCompleta();
			 List<Long> carteCompletaIds = new ArrayList<Long>();
			 for(UploadedArticle c: carteCompletaList ) {
				 carteCompletaIds.add(c.getUploadedArticleId());
			 }
		 }
		 
		 if(conferinta.equals("true")) {
			 List<UploadedArticle> confs = this.getArticoleConferinta();
			 List<Long> confsIds = new ArrayList<Long>();
			 for(UploadedArticle cc: confs) {
				 confsIds.add(cc.getUploadedArticleId());
			 }
		 }
		 
//		 System.out.println("Jurnal: " + jurnal);
//		 System.out.println("Carte capitol: " + carteCapitol);
//		 System.out.println("Carte completa: " + carteCompleta);
//		 System.out.println("Conferinta: " + conferinta);
//		 System.out.println("title: " + title);
//		 System.out.println("author:" + author);
//		 System.out.println("tag: " + tag);
//		 System.out.println("an: " + an);
		 
		 
		 return new ResponseEntity<Response>(new Response("Succes response"), new HttpHeaders(), HttpStatus.OK);
		 
	 }

}
