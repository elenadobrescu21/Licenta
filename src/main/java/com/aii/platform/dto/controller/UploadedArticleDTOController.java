package com.aii.platform.dto.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aii.platform.dto.AppUserDTO;
import com.aii.platform.dto.CarteCapitolDTO;
import com.aii.platform.dto.CarteCompletaDTO;
import com.aii.platform.dto.ConferintaDTO;
import com.aii.platform.dto.JurnalRevistaDTO;
import com.aii.platform.dto.UploadedArticleDTO;
import com.aii.platform.dto.converter.UploadedArticleConverter;
import com.aii.platform.models.AppUser;
import com.aii.platform.models.CarteCapitol;
import com.aii.platform.models.CarteCompleta;
import com.aii.platform.models.Conferinta;
import com.aii.platform.models.JurnalRevista;
import com.aii.platform.models.Tag;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.CarteCapitolService;
import com.aii.platform.service.CarteCompletaService;
import com.aii.platform.service.ConferintaService;
import com.aii.platform.service.JurnalRevistaService;
import com.aii.platform.service.TagService;
import com.aii.platform.service.UploadedArticleService;

@Controller
public class UploadedArticleDTOController {
	
	@Autowired
	private UploadedArticleConverter uploadedArticleConverter;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	@Autowired
	private CarteCompletaService carteCompletaService;
	
	@Autowired
	private ConferintaService conferintaService;
	
	@Autowired
	private CarteCapitolService carteCapitolService;
	
	@Autowired
	private JurnalRevistaService jurnalRevistaService;
	

	@RequestMapping(value="/articleDTO/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleDTOById(@PathVariable(value="id")Long articleId){
		
		UploadedArticleDTO uploadedArticleDTO = uploadedArticleConverter.convertUploadedArticleToDTO(articleId);
		

		if(uploadedArticleDTO.getIdTipArticol() == 1) {
			System.out.println("Este carte completa");
			CarteCompleta carteCompleta = carteCompletaService.getCarteCompletaById(articleId);
			//System.out.println(carteCompleta.getEditura());
			CarteCompletaDTO carteCompletaDTO = new CarteCompletaDTO(uploadedArticleDTO.getId(),
					uploadedArticleDTO.getTitlu(), uploadedArticleDTO.getFilePath(), 
					uploadedArticleDTO.getNumberOfDownloads(), uploadedArticleDTO.getUploadedOn(),
					uploadedArticleDTO.getOwner(),
					uploadedArticleDTO.getCoauthors(), uploadedArticleDTO.getTags(), 
					uploadedArticleDTO.getIdTipArticol(), uploadedArticleDTO.getTipArticol(),
					carteCompleta.getEditura(),
					carteCompleta.getEditie(), 
					carteCompleta.getIsbn(),
					carteCompleta.getIssn(), 
					carteCompleta.getAnPublicare());
			return new ResponseEntity<CarteCompletaDTO>(carteCompletaDTO, new HttpHeaders(), HttpStatus.OK);
		}
		
		if(uploadedArticleDTO.getIdTipArticol() == 2L) {
			System.out.println("este carte capitol");
			CarteCapitol carteCapitol = carteCapitolService.getCarteCapitolById(articleId);
			
			CarteCapitolDTO carteCapitolDTO = new CarteCapitolDTO(uploadedArticleDTO.getId(),
					uploadedArticleDTO.getTitlu(), uploadedArticleDTO.getFilePath(), 
					uploadedArticleDTO.getNumberOfDownloads(), uploadedArticleDTO.getUploadedOn(),
					uploadedArticleDTO.getOwner(),
					uploadedArticleDTO.getCoauthors(), uploadedArticleDTO.getTags(), 
					uploadedArticleDTO.getIdTipArticol(), uploadedArticleDTO.getTipArticol(),
					carteCapitol.getTitlu(), carteCapitol.getAutoriCarte(), carteCapitol.getEditoriCarte(), 
					carteCapitol.getNumeCapitol(),
					carteCapitol.getPaginaInceput(),carteCapitol.getPaginaSfarsit(), carteCapitol.getAnPublicare(), 
					carteCapitol.getEditura(),
					carteCapitol.getEditie(),
					carteCapitol.getIsbn(),
					carteCapitol.getIssn());
		return new ResponseEntity<CarteCapitolDTO>(carteCapitolDTO, new HttpHeaders(), HttpStatus.OK);
			
		}
		
		if(uploadedArticleDTO.getIdTipArticol() == 3) {
			
			Conferinta conferinta = conferintaService.getConferintaById(articleId);
			ConferintaDTO conferintaDTO = new ConferintaDTO(uploadedArticleDTO.getId(),
					uploadedArticleDTO.getTitlu(), uploadedArticleDTO.getFilePath(), 
					uploadedArticleDTO.getNumberOfDownloads(), uploadedArticleDTO.getUploadedOn(),
					uploadedArticleDTO.getOwner(),
					uploadedArticleDTO.getCoauthors(), uploadedArticleDTO.getTags(), 
					uploadedArticleDTO.getIdTipArticol(), uploadedArticleDTO.getTipArticol(),
					conferinta.getNume(),
					conferinta.getLocatie(),
					conferinta.getData());
		System.out.println("Este conferinta");
		
		return new ResponseEntity<ConferintaDTO>(conferintaDTO, new HttpHeaders(), HttpStatus.OK);
		}
		
		if(uploadedArticleDTO.getIdTipArticol() == 4L) {
			JurnalRevista jurnalRevista = jurnalRevistaService.getJurnalRevistaById(articleId);
			JurnalRevistaDTO jurnalRevistaDTO = new JurnalRevistaDTO(uploadedArticleDTO.getId(),
					uploadedArticleDTO.getTitlu(), uploadedArticleDTO.getFilePath(), 
					uploadedArticleDTO.getNumberOfDownloads(), uploadedArticleDTO.getUploadedOn(),
					uploadedArticleDTO.getOwner(),
					uploadedArticleDTO.getCoauthors(), uploadedArticleDTO.getTags(), 
					uploadedArticleDTO.getIdTipArticol(), uploadedArticleDTO.getTipArticol(),
					jurnalRevista.getTitlu(),
					jurnalRevista.getNumar(),
					jurnalRevista.getVolum(),
					jurnalRevista.getPaginaStart(),
					jurnalRevista.getPaginaSfarsit(),
					jurnalRevista.getIssn(),
					jurnalRevista.getIsbn(),
					jurnalRevista.getDataAparitie());
			
		return new ResponseEntity<JurnalRevistaDTO>(jurnalRevistaDTO, new HttpHeaders(), HttpStatus.OK);
		}
				
	return new ResponseEntity<UploadedArticleDTO>(uploadedArticleDTO, new HttpHeaders(), HttpStatus.OK);
				
	}
	
	@RequestMapping(value="/articleDTO/latest", method = RequestMethod.GET)
	public ResponseEntity<?> getLatestArticleDTO() {
		List<UploadedArticleDTO> latestArticles = uploadedArticleConverter.convertLatestArticleListToDTO();
		return new ResponseEntity<List<UploadedArticleDTO>>(latestArticles, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/articleDTO/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArticlesDTO(){
		List<UploadedArticleDTO> allArticlesDTO = uploadedArticleConverter.convertArticleListToDTO();
		
		return new ResponseEntity<List<UploadedArticleDTO>>(allArticlesDTO, new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/articleDTO/findByTag/{tag}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticlesByTag(@PathVariable(value="tag")String tag) {
		List<UploadedArticle> articlesByTag = uploadedArticleService.getAllArticlesByDenumireTag(tag);
		List<UploadedArticleDTO> articlesByTagDTO = uploadedArticleConverter.convertListToDTO(articlesByTag);
		
		return new ResponseEntity<List<UploadedArticleDTO>>(articlesByTagDTO, new HttpHeaders(), HttpStatus.OK);
		
	}
	

}
