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

import com.aii.platform.service.TipArticolService;
import com.aii.platform.models.*;

@Controller
public class TipArticolController {
	
	@Autowired
	private TipArticolService tipArticolService;
	
	@RequestMapping(value="/tipArticol", method = RequestMethod.GET)
	public ResponseEntity<?> getAllTipArticol() {
		return new ResponseEntity<List<TipArticol>>(tipArticolService.getAllTipArticole(), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/tipArticol/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllTipArticolById(@PathVariable("id") Long id) {
		return new ResponseEntity<TipArticol>(tipArticolService.getTipArticolById(id), new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/tipArticol/articol/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllTipArticolByArticleId(@PathVariable("id") Long id) {
		return new ResponseEntity<TipArticol>(tipArticolService.getTipArticolByArticleId(id), new HttpHeaders(), HttpStatus.OK);
	}
	

}
