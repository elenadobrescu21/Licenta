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
import com.aii.platform.repository.UploadedArticleRepository;
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
	
	@Autowired
	private UploadedArticleRepository uploadedArticleRepository;
	
	
 	 @RequestMapping(value="/query", method =RequestMethod.POST)
	 public ResponseEntity<?> searchArticle(HttpServletRequest request) {
		 
		 String articleType = request.getParameter("article-type").replace("\"", "");
		 
		 String title = request.getParameter("title").replace("\"", "");
		 String author = request.getParameter("author").replace("\"", "");;
		 String tag = request.getParameter("tag").replace("\"", "");;
		 String an = request.getParameter("an").replace("\"", "");
		 
		 System.out.println("Article type: "+ articleType);
		 
		 System.out.println("title: " + title);
		 System.out.println("author:" + author);
		 System.out.println("tag: " + tag);
		 System.out.println("an: " + an);
		 
		 String nume = "";
		 String prenume = "";
		 
		 if(!author.equals("undefined")) {
			 String[] splited = author.split(" ");
			 nume = splited[0];
			 prenume = splited[0];
		 }
		 
		 int year = 0;
		 if(!an.equals("undefined")) {
			 year = Integer.parseInt(an);
		 }
		 
		 //cautare dupa tip de articol
		 
		 if(articleType.equals("carte-completa") && title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleService.getArticlesByTipArticolId(1L), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleService.getArticlesByTipArticolId(2L), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleService.getArticlesByTipArticolId(3L), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleService.getArticlesByTipArticolId(4L), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa tip si titlu
		 if(articleType.equals("carte-completa") && !title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByArticleTypeAndTitle(1L, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && !title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByArticleTypeAndTitle(2L, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && !title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByArticleTypeAndTitle(3L, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && !title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByArticleTypeAndTitle(4L, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa tip si tag
		 if(articleType.equals("carte-completa") && title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolAndTag(1L, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolAndTag(2L, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && title.equals("undefined") && 
				!tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolAndTag(3L, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolAndTag(4L, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa tip+autor
		 
		 if(articleType.equals("carte-completa") && title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolOwnerAutor(1L, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolOwnerAutor(2L, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && title.equals("undefined") && 
				tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolOwnerAutor(3L, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolOwnerAutor(4L, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa tip si an
		 
		 if(articleType.equals("carte-completa") && title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCompletaByYear(year), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCapitolByYear(year), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && title.equals("undefined") && 
				tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findConferintaByYear(year), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findJurnalRevistaByYear(year), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa tip, titlu si tag
		 
		 if(articleType.equals("carte-completa") && !title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTagAndTitle(1L, title, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && !title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTagAndTitle(2L, title, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && !title.equals("undefined") && 
				!tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTagAndTitle(3L, title, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && !title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTagAndTitle(4L, title, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa titlu si an
		 
		 if(articleType.equals("carte-completa") && !title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCompletaByTitleAndYear(year, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && !title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCapitolByTitleAndYear(year,title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && !title.equals("undefined") && 
				tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findConferintaByTitleAndYear(year,title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && !title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findJurnalRevistaByTitleAndYear(year, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa titlu si autor
		 
		 if(articleType.equals("carte-completa") && !title.equals("undefined") && 
				 tag.equals("undefined") && author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTitleAndAuthor(1L, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && !title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTitleAndAuthor(2L, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && !title.equals("undefined") && 
				tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTitleAndAuthor(3L, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && !title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTitleAndAuthor(4L, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa  tag si autor
		 
		 if(articleType.equals("carte-completa") && title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolAndTagAndAuthor(1L, tag, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolAndTagAndAuthor(2L, tag, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && title.equals("undefined") && 
				!tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolAndTagAndAuthor(3L, tag, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findByTipArticolAndTagAndAuthor(4L, tag, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa tag + an
		 
		 if(articleType.equals("carte-completa") && title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCompletaByYearAndTag(year, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCapitolByYearAndTag(year,tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && title.equals("undefined") && 
				!tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findConferintaByYearAndTag(year,tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findJurnalRevistaByYearAndTag(year, tag), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa autor si an
		 
		 if(articleType.equals("carte-completa") && title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCompletaByYearAndAutorOwner(year, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCapitolByYearAndAutorOwner(year, nume,prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && title.equals("undefined") && 
				!tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findConferintaByYearAndAutorOwner(year,nume,prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findJurnalRevistaByYearAndAutorOwner(year,nume,prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa titlu + tag + an
		 
		 if(articleType.equals("carte-completa") && !title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCompletaByTitleTagAndYear(year, 1L, tag, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && !title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCapitolByTitleTagAndYear(year,2L, tag, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && !title.equals("undefined") && 
				!tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findConferintaByTitleTagAndYear(year,3L, tag, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && !title.equals("undefined") && 
				 !tag.equals("undefined") && author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findJurnalRevistaByTitleTagAndYear(year, 4L, tag, title), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa titlu, an, autor
		 
		 if(articleType.equals("carte-completa") && !title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCompletaByTitleYearAndAuthor(year, 1L, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && !title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCapitolByTitleYearAndAuthor(year, 2L, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && !title.equals("undefined") && 
				tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findConferintaByTitleYearAndAuthor(year,3L, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && !title.equals("undefined") && 
				 tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findJurnalRevistaByTitleYearAndAuthor(year, 4L, title, nume,prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 //cautare dupa tag, an, autor
		 
		 if(articleType.equals("carte-completa") && title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCompletaByTagYearAuthor(year, 1L, tag, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCapitolByTagYearAuthor(year,2L, tag, nume,prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && title.equals("undefined") && 
				!tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findConferintaTagYearAuthor(year, 3L, tag, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findJurnalRevistaByTagYearAuthor(year, 4L, tag,nume,prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
	
		 //cautare dupa toate criteriile
		 
		 if(articleType.equals("carte-completa") && !title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCompletaByAllCriteria(year, 1L, tag, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("carte-capitol") && !title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCapitolByAllCriteria(year, 2L, tag, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("conferinta") && !title.equals("undefined") && 
				!tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findCarteCapitolByAllCriteria(year, 3L, tag, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
		 if(articleType.equals("jurnal") && title.equals("undefined") && 
				 !tag.equals("undefined") && !author.equals("undefined") && !an.equals("undefined")) {
			 return new ResponseEntity<List<UploadedArticle>>(uploadedArticleRepository.findJurnalRevistaByAllCriteria(year, 4l, tag, title, nume, prenume), new HttpHeaders(), HttpStatus.OK);
		 }
		 
			 
		 return new ResponseEntity<Response>(new Response("Nu au fost gasite articole"), new HttpHeaders(), HttpStatus.NOT_FOUND);
		 
	 }

}
