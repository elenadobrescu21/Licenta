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

import com.aii.platform.dto.AppUserDTO;
import com.aii.platform.dto.UploadedArticleDTO;
import com.aii.platform.dto.converter.AppUserConverter;

@Controller
public class AppUserDTOController {
	
	@Autowired
	private AppUserConverter appUserConverter;
	
	@RequestMapping(value="appUserDTO/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsersDTO() {
		List<AppUserDTO> allUsersDTO = appUserConverter.convertAllAppUsersToDTO();
		
		return new ResponseEntity<List<AppUserDTO>>(allUsersDTO, new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/userDTO/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleDTOById(@PathVariable(value="id")Long appUserId){
		
		AppUserDTO userDTO = appUserConverter.convertToDTOByAppUserId(appUserId);
		
		return new ResponseEntity<AppUserDTO>(userDTO, new HttpHeaders(), HttpStatus.OK);
				
	}
	
	

}
