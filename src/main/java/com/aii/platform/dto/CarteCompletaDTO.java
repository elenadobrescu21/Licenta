package com.aii.platform.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class CarteCompletaDTO extends UploadedArticleDTO {
		
	private String editura;
	
	private String editie;
	
	private String isbn;

	private String issn;
	
	private int anPublicare;
	
	
	public CarteCompletaDTO(String editura, String editie, String isbn, String issn, int anPublicare) {
		super();
		this.editura = editura;
		this.editie = editie;
		this.isbn = isbn;
		this.issn = issn;
		this.anPublicare = anPublicare;
	}



	public CarteCompletaDTO(Long id, String titlu, String filepath, int numberOfDownloads, Date date, AppUserDTO owner,
			List<AppUserDTO> coauthors, String[] tags, Long idTipArticol, String tipArticol, String abstractSection,
			String wos, String doi,
			String editura, String editie, String isbn, String issn, int anPublicare) {
		super(id, titlu, filepath, numberOfDownloads, date, owner, coauthors, tags, idTipArticol, tipArticol, abstractSection, wos, doi);
		this.editura = editura;
		this.editie = editie;
		this.isbn = isbn;
		this.issn = issn;
		this.anPublicare = anPublicare;
	}



	public String getEditura() {
		return editura;
	}



	public void setEditura(String editura) {
		this.editura = editura;
	}



	public String getEditie() {
		return editie;
	}



	public void setEditie(String editie) {
		this.editie = editie;
	}



	public String getIsbn() {
		return isbn;
	}



	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}



	public String getIssn() {
		return issn;
	}



	public void setIssn(String issn) {
		this.issn = issn;
	}



	public int getAnPublicare() {
		return anPublicare;
	}



	public void setAnPublicare(int anPublicare) {
		this.anPublicare = anPublicare;
	}
	


}
