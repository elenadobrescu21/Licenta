package com.aii.platform.dto.controller;

import java.util.Collections;
import java.util.Comparator;
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
	
	@RequestMapping(value="appUserDTO/top", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsersDTOTop() {
		List<AppUserDTO> allUsersDTO = appUserConverter.convertAllAppUsersToDTO();
		Collections.sort(allUsersDTO);
		return new ResponseEntity<List<AppUserDTO>>(allUsersDTO, new HttpHeaders(), HttpStatus.OK);	
	}
	
	@RequestMapping(value="appUserDTO/first", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsersDTOFirst() {
		List<AppUserDTO> allUsersDTO = appUserConverter.convertAllAppUsersToDTO();
		Collections.sort(allUsersDTO);
		return new ResponseEntity<AppUserDTO>(allUsersDTO.get(0), new HttpHeaders(), HttpStatus.OK);	
	}
	
	@RequestMapping(value="appUserDTO/topDownloads", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsersDTOTopDownloads() {
		List<AppUserDTO> allUsersDTO = appUserConverter.convertAllAppUsersToDTO();
		Collections.sort(allUsersDTO, new Comparator<AppUserDTO>() {
			public int compare(AppUserDTO a1, AppUserDTO a2) {
				return a2.getTotalNumberOfDownloads() - a1.getTotalNumberOfDownloads();
			}
		});
		
		return new ResponseEntity<List<AppUserDTO>>(allUsersDTO, new HttpHeaders(), HttpStatus.OK);	
	}
	
	@RequestMapping(value="appUserDTO/topDownloadsFirst", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsersDTOTopDownloadsFirst() {
		List<AppUserDTO> allUsersDTO = appUserConverter.convertAllAppUsersToDTO();
		Collections.sort(allUsersDTO, new Comparator<AppUserDTO>() {
			public int compare(AppUserDTO a1, AppUserDTO a2) {
				return a2.getTotalNumberOfDownloads() - a1.getTotalNumberOfDownloads();
			}
		});
		
		return new ResponseEntity<AppUserDTO>(allUsersDTO.get(0), new HttpHeaders(), HttpStatus.OK);	
	}
	
	
	@RequestMapping(value="/userDTO/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleDTOById(@PathVariable(value="id")Long appUserId){
		
		AppUserDTO userDTO = appUserConverter.convertToDTOByAppUserId(appUserId);
		
		return new ResponseEntity<AppUserDTO>(userDTO, new HttpHeaders(), HttpStatus.OK);
				
	}
	
	

}
