package com.aii.platform.service;

import java.util.Date;
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
	
	public void updateJurnalRevista(Long id, String titlu, int numar, int volum, int paginaStart, int paginaSfarsit,
			String issn, String isbn, Date dataAparitie,
			String abstractSection, String articleTitle, String wos, String doi) {
		
		JurnalRevista jurnalRevista = jurnalRevistaRepository.findOne(id);
		jurnalRevista.setTitlu(titlu);
		jurnalRevista.setNumar(numar);
		jurnalRevista.setVolum(volum);
		jurnalRevista.setPaginaStart(paginaStart);
		jurnalRevista.setPaginaSfarsit(paginaSfarsit);
		jurnalRevista.setIssn(issn);
		jurnalRevista.setIsbn(isbn);
		jurnalRevista.setDataAparitie(dataAparitie);
		jurnalRevista.getUploadedArticle().setAbstractSection(abstractSection);
		jurnalRevista.getUploadedArticle().setTitle(articleTitle);
		jurnalRevista.getUploadedArticle().setWos(wos);
		jurnalRevista.getUploadedArticle().setDoi(doi);
		
		jurnalRevistaRepository.save(jurnalRevista);
		
	}
	
	public void deleteJurnalRevista(Long id) {
		jurnalRevistaRepository.delete(id);
	}
	
	public JurnalRevista getJurnalRevistaById(Long id) {
		return jurnalRevistaRepository.findOne(id);
	}
	
	public List<JurnalRevista> getJurnalRevistaByYear(int year) {
		return jurnalRevistaRepository.findByYear(year);
	}
	
	
}
