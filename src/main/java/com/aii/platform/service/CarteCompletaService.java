package com.aii.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.models.CarteCompleta;
import com.aii.platform.repository.CarteCompletaRepository;

@Service
public class CarteCompletaService {
	
	@Autowired
	private CarteCompletaRepository carteCompletaRepository;
	
	
	public CarteCompleta saveCarteCompleta(CarteCompleta carteCompleta) {
		return carteCompletaRepository.save(carteCompleta);
	}
	
	public void updateCarteCompleta(Long id, String editura, String editie, String isbn, String issn, int anPublicare, 
			String abstractSection, String articleTitle, String wos, String doi) {
		CarteCompleta carteCompleta = carteCompletaRepository.findOne(id);
		carteCompleta.setEditura(editura);
		carteCompleta.setEditie(editie);
		carteCompleta.setIsbn(isbn);
		carteCompleta.setIssn(issn);
		carteCompleta.setAnPublicare(anPublicare);
		carteCompleta.getUploadedArticle().setAbstractSection(abstractSection);
		carteCompleta.getUploadedArticle().setTitle(articleTitle);
		carteCompleta.getUploadedArticle().setWos(wos);
		carteCompleta.getUploadedArticle().setDoi(doi);
		carteCompletaRepository.save(carteCompleta);
	}
	
	public void deleteCarteCompleta(Long id) {
		carteCompletaRepository.delete(id);
	}
	
	public CarteCompleta getCarteCompletaById(Long id) {
		return carteCompletaRepository.findOne(id);
	}
	
	public List<CarteCompleta> getCarteCompletaByYear(int year) {
		return carteCompletaRepository.findByAnPublicare(year);
	}
	
	

}
