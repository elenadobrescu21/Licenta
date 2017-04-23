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

import com.aii.platform.models.Tag;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.repository.TagRepository;
import com.aii.platform.response.Response;
import com.aii.platform.service.TagService;

@Controller
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@RequestMapping(value="/tag/{denumire}", method = RequestMethod.GET)
	public ResponseEntity<?> getTagByDenumire(@PathVariable("denumire") String denumire) {
		if(tagService.getTagByDenumire(denumire)!=null) {
			return new ResponseEntity<Tag>(tagService.getTagByDenumire(denumire), new HttpHeaders(), HttpStatus.OK);		
		} else {
			return new ResponseEntity<Response>(new Response("Tag couldn't be found"), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}		
	}
	
	@RequestMapping(value="/tag", method = RequestMethod.GET)
	public ResponseEntity<?> getAllTags(){
		if(tagService.getAllTags() != null) {
			return new ResponseEntity<List<Tag>>(tagService.getAllTags(), new HttpHeaders(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response("Nu exista tag-uri"), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
	}
	
	

}
