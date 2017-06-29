package com.aii.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.models.CarteCapitol;
import com.aii.platform.repository.CarteCapitolRepository;

@Service
public class CarteCapitolService {
	
	@Autowired
	private CarteCapitolRepository carteCapitolRepository;
	
	
	public CarteCapitol saveCarteCapitol(CarteCapitol carteCapitol) {
		return carteCapitolRepository.save(carteCapitol);
	}
	
	public void updateCarteCapitol(Long id, String titluCarte, String autoriCarte, String editoriCarte,
			String numeCapitol, int paginaInceput, int paginaSfarsit, String editura, String editie, String ISBN,
			String ISSN, String abstractSection, String articleTitle, String wos, String doi) {
		CarteCapitol carteCapitol = carteCapitolRepository.findOne(id);
		carteCapitol.setTitlu(titluCarte);
		carteCapitol.setAutoriCarte(autoriCarte);
		carteCapitol.setEditoriCarte(editoriCarte);
		carteCapitol.setNumeCapitol(numeCapitol);
		carteCapitol.setPaginaInceput(paginaInceput);
		carteCapitol.setPaginaSfarsit(paginaSfarsit);
		carteCapitol.setEditura(editura);
		carteCapitol.setEditie(editie);
		carteCapitol.setIsbn(ISBN);
		carteCapitol.setIssn(ISSN);
		carteCapitol.getUploadedArticle().setAbstractSection(abstractSection);
		carteCapitol.getUploadedArticle().setTitle(articleTitle);
		carteCapitol.getUploadedArticle().setWos(wos);
		carteCapitol.getUploadedArticle().setDoi(doi);
		carteCapitolRepository.save(carteCapitol);
	}
	
	public void deleteCarteCapitol(Long id) {
		carteCapitolRepository.delete(id);
	}
	
	public CarteCapitol getCarteCapitolById(Long id) {
		return carteCapitolRepository.findOne(id);
	}
	
	public List<CarteCapitol> getCarteCapitolByYear(int year) {
		return carteCapitolRepository.findByAnPublicare(year);
	}
	
	

}
