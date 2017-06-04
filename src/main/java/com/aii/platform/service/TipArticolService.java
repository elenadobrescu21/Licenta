package com.aii.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.repository.TipArticolRepository;
import com.aii.platform.models.*;

@Service
public class TipArticolService {
	
	@Autowired
	private TipArticolRepository tipArticolRepository;
	
	public List<TipArticol> getAllTipArticole(){
		return tipArticolRepository.findAll();
	}
	
	public TipArticol getTipArticolById(Long id) {
		return tipArticolRepository.findOne(id);
	}
	
	public TipArticol getTipArticolByArticleId(Long id) {
		return tipArticolRepository.findByArticoleUploadedArticleId(id);
	}
}
