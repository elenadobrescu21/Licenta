package com.aii.platform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.Coauthor;
import com.aii.platform.service.CoauthorService;

@Controller
public class CoauthorController {
	
	@Autowired
	private CoauthorService coauthorService;
	
	
	@RequestMapping(value="/coauthor/{articleId}", method = RequestMethod.GET)
	public ResponseEntity<?> getCoauthorsByArticleID(@PathVariable("articleId")Long articleId) {
		List<Coauthor> coauthors = coauthorService.getCoauthorByUploadedArticleId(articleId);

		return new ResponseEntity<List<Coauthor>>(coauthors, new HttpHeaders(), HttpStatus.OK);
	}
	
	

}
