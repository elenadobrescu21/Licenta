package com.aii.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.models.JurnalRevista;
import com.aii.platform.repository.JurnalRevistaRepository;

@Service
public class JurnalRevistaService {
	
	@Autowired
	private JurnalRevistaRepository jurnalRevistaRepository;
	
	public JurnalRevista saveJurnalRevista(JurnalRevista jurnalRevista) {
		return jurnalRevistaRepository.save(jurnalRevista);
		
	}
	
	public JurnalRevista getJurnalRevistaById(Long id) {
		return jurnalRevistaRepository.findOne(id);
	}
	
	public List<JurnalRevista> getJurnalRevistaByYear(int year) {
		return jurnalRevistaRepository.findByYear(year);
	}
	
	
}
