package com.aii.platform.service;

import java.util.Date;
import java.util.List;

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
	
	public void updateConferinta(Long id, String nume, String locatie, Date data,
			String abstractSection, String articleTitle, String wos, String doi) {
		Conferinta conferinta = conferintaRepository.findOne(id);
		conferinta.setNume(nume);
		conferinta.setLocatie(locatie);
		conferinta.setData(data);
		conferinta.getUploadedArticle().setAbstractSection(abstractSection);
		conferinta.getUploadedArticle().setTitle(articleTitle);
		conferinta.getUploadedArticle().setWos(wos);
		conferinta.getUploadedArticle().setDoi(doi);
		conferintaRepository.save(conferinta);
		
	}
	public void deleteConferinta(Long id) {
		conferintaRepository.delete(id);
	}
	
	public Conferinta getConferintaById(Long id) {
		return conferintaRepository.findOne(id);
	}
	
	public List<Conferinta> getConferintaByYear(int year) {
		return conferintaRepository.findByYear(year);
	}
	
	
}
