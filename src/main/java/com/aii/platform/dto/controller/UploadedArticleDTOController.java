package com.aii.platform.dto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aii.platform.dto.UploadedArticleDTO;
import com.aii.platform.dto.converter.UploadedArticleConverter;
import com.aii.platform.models.AppUser;
import com.aii.platform.models.Tag;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.TagService;
import com.aii.platform.service.UploadedArticleService;

@Controller
public class UploadedArticleDTOController {
	
	@Autowired
	private UploadedArticleConverter uploadedArticleConverter;
	
	
	@RequestMapping(value="/articleDTO/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleDTOById(@PathVariable(value="id")Long articleId){
		
		UploadedArticleDTO uploadedArticleDTO = uploadedArticleConverter.convertUploadedArticleToDTO(articleId);
		
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
	

}
