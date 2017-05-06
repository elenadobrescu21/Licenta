package com.aii.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.repository.ConferintaRepository;

import com.aii.platform.models.Conferinta;

@Service
public class ConferintaService {
	
	@Autowired
	private ConferintaRepository conferintaRepository;
	
	public Conferinta saveConferinta(Conferinta conferinta) {
		return conferintaRepository.save(conferinta);
	}
	
	public Conferinta getConferintaById(Long id) {
		return conferintaRepository.findOne(id);
	}
	
	

}
