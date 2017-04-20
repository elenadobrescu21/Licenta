package com.aii.platform.dto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aii.platform.dto.converter.AppUserExtendedConverter;

import com.aii.platform.dto.*;

@Controller
public class AppUserDTOExtendedController {
	
	@Autowired
	private AppUserExtendedConverter appUserExtendedConverter;
	
	
	@RequestMapping(value="/userDTOExtended/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserDTOExtendedById(@PathVariable("id") Long appUserId) {
		
	return new ResponseEntity<AppUserDTOExtended>(appUserExtendedConverter.convertToDTOByAppUserId(appUserId), new HttpHeaders(), HttpStatus.OK);
		
		
	}

}
