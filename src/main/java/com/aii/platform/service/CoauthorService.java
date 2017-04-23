package com.aii.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.models.Coauthor;
import com.aii.platform.repository.CoauthorRepository;

@Service
public class CoauthorService {
	
	@Autowired
	private CoauthorRepository coauthorRepository;
	
	public List<Coauthor> getAllCoauthors() {
		return coauthorRepository.findAll();
	}
	
	public Coauthor getCoauthorById(Long id) {
		return coauthorRepository.findOne(id);
	}
	
	public Coauthor getCoauthorByFullname(String fullname) {
		return coauthorRepository.findByFullname(fullname);
	}
	
	public Coauthor saveCoauthor(Coauthor coauthor) {
		return coauthorRepository.save(coauthor);
	}
	

}
