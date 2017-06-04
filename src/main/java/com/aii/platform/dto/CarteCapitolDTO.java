package com.aii.platform.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class CarteCapitolDTO extends UploadedArticleDTO{

	private String titluCarte;
	
	private String autoriCarte;
	
	private String editoriCarte;
		
	private String numeCapitol;
	
	private int paginaInceput;
		
	private int paginaSfarsit;
		
	private int anPublicare;
	
	private String editura;
	
	private String editie;
	
	private String isbn;
	
	private String issn;

	public CarteCapitolDTO(Long id, String titlu, String filepath, int numberOfDownloads, Date date, AppUserDTO owner,
			List<AppUserDTO> coauthors, String[] tags, Long idTipArticol, String tipArticol,
			String titluCarte, String autoriCarte, String editoriCarte, String numeCapitol,
			int paginaInceput, int paginaSfarsit, int anPublicare, String editura, String editie, String isbn,
			String issn) {
		super(id, titlu, filepath, numberOfDownloads, date, owner, coauthors, tags, idTipArticol, tipArticol);
		this.titluCarte = titluCarte;
		this.autoriCarte = autoriCarte;
		this.editoriCarte = editoriCarte;
		this.numeCapitol = numeCapitol;
		this.paginaInceput = paginaInceput;
		this.paginaSfarsit = paginaSfarsit;
		this.anPublicare = anPublicare;
		this.editura = editura;
		this.editie = editie;
		this.isbn = isbn;
		this.issn = issn;
	}

	public CarteCapitolDTO(String titluCarte, String autoriCarte, String editoriCarte, String numeCapitol,
			int paginaInceput, int paginaSfarsit, int anPublicare, String editura, String editie, String isbn,
			String issn) {
		super();
		this.titluCarte = titluCarte;
		this.autoriCarte = autoriCarte;
		this.editoriCarte = editoriCarte;
		this.numeCapitol = numeCapitol;
		this.paginaInceput = paginaInceput;
		this.paginaSfarsit = paginaSfarsit;
		this.anPublicare = anPublicare;
		this.editura = editura;
		this.editie = editie;
		this.isbn = isbn;
		this.issn = issn;
	}

	public String getTitluCarte() {
		return titluCarte;
	}

	public void setTitluCarte(String titluCarte) {
		this.titluCarte = titluCarte;
	}

	public String getAutoriCarte() {
		return autoriCarte;
	}

	public void setAutoriCarte(String autoriCarte) {
		this.autoriCarte = autoriCarte;
	}

	public String getEditoriCarte() {
		return editoriCarte;
	}

	public void setEditoriCarte(String editoriCarte) {
		this.editoriCarte = editoriCarte;
	}

	public String getNumeCapitol() {
		return numeCapitol;
	}

	public void setNumeCapitol(String numeCapitol) {
		this.numeCapitol = numeCapitol;
	}

	public int getPaginaInceput() {
		return paginaInceput;
	}

	public void setPaginaInceput(int paginaInceput) {
		this.paginaInceput = paginaInceput;
	}

	public int getPaginaSfarsit() {
		return paginaSfarsit;
	}

	public void setPaginaSfarsit(int paginaSfarsit) {
		this.paginaSfarsit = paginaSfarsit;
	}

	public int getAnPublicare() {
		return anPublicare;
	}

	public void setAnPublicare(int anPublicare) {
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
	
	
	
	

}
